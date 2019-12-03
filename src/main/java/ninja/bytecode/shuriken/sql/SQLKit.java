package ninja.bytecode.shuriken.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import ninja.bytecode.shuriken.collections.GList;
import ninja.bytecode.shuriken.collections.GMap;
import ninja.bytecode.shuriken.collections.GSet;
import ninja.bytecode.shuriken.execution.J;
import ninja.bytecode.shuriken.logging.L;
import ninja.bytecode.shuriken.math.M;

public class SQLKit
{
	private Supplier<Connection> sql;
	private long lastTest;
	private boolean logging;
	private GSet<String> existingTables;
	private String sqlAddress = "localhost";
	private String sqlDatabase = "ordis";
	private String sqlUsername = "ordis";
	private String sqlPassword = "12345";
	private int sqlPort = 3306;
	private boolean pooling;
	private int poolSize;
	private GList<Connection> pool;
	private long cwait = 1000;
	private String driver;
	private GList<SQLListener> listeners;

	public SQLKit(Connection sql, boolean log)
	{
		listeners = new GList<>();
		lastTest = M.ms();
		existingTables = new GSet<String>();
		this.sqlAddress = "none";
		this.sqlDatabase = sqlAddress;
		this.sqlUsername = sqlAddress;
		this.sqlPassword = sqlAddress;
		this.sql = () -> sql;
		logging = log;
		driver = "com.mysql.jdbc.Driver";
	}
	
	public void addListener(SQLListener s)
	{
		listeners.add(s);
	}

	public SQLKit(String sqlAddress, String sqlDatabase, String sqlUsername, String sqlPassword)
	{
		this(sqlAddress, sqlDatabase, sqlUsername, sqlPassword, false, 0);
	}

	public SQLKit(String sqlAddress, String sqlDatabase, String sqlUsername, String sqlPassword, boolean pooling, int poolSize)
	{
		this.pooling = pooling;
		this.poolSize = poolSize;
		pool = new GList<>();
		lastTest = M.ms();
		existingTables = new GSet<String>();
		this.sqlAddress = sqlAddress;
		this.sqlDatabase = sqlDatabase;
		this.sqlUsername = sqlUsername;
		this.sqlPassword = sqlPassword;
		driver = "com.mysql.jdbc.Driver";
	}

	public void setLogging(boolean l)
	{
		this.logging = l;
	}

	public void setPort(int port)
	{
		this.sqlPort = port;
	}

	public void start() throws SQLException
	{
		if(pooling)
		{
			Connection c = createNewConnection("P1");
			pool.add(c);

			if(poolSize - 1 > 0)
			{
				J.a(() ->
				{
					for(int i = 0; i < poolSize - 1; i++)
					{
						try
						{
							Connection cx = createNewConnection("P" + (i + 2));
							pool.add(cx);
							
							try
							{
								Thread.sleep(cwait);
							}
							
							catch(InterruptedException e)
							{
								
							}
						}

						catch(SQLException e)
						{
							L.ex(e);
						}
					}
				});
			}

			sql = () -> pool.getRandom();
		}

		else
		{
			Connection c = createNewConnection("Main");
			sql = () -> c;
		}
	}

	private Connection createNewConnection(String id) throws SQLException
	{
		Connection c = null;
		l(id + " -> Connecting to MySQL jdbc:mysql://" + sqlAddress + "/" + sqlDatabase + "?username=" + sqlUsername + "&password=" + sqlPassword);

		try
		{
			Class.forName(driver).newInstance();
			Properties p = new Properties();
			p.setProperty("user", sqlUsername);

			if(!sqlPassword.equals("."))
			{
				p.setProperty("password", sqlPassword);
			}

			c = DriverManager.getConnection("jdbc:mysql://" + sqlAddress + (sqlPort != 3306 ? (":" + sqlPort) : "") + "/" + sqlDatabase, p);
		}

		catch(InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			L.ex(e);
			f(id + " -> Failed to instantiate " + driver);
			throw new SQLException(id + " -> SQL Driver Failure");
		}

		catch(SQLException e)
		{
			L.ex(e);
			f(id + " -> SQLException: " + e.getMessage());
			f(id + " -> SQLState: " + e.getSQLState());
			f(id + " -> VendorError: " + e.getErrorCode());
			throw new SQLException(id + " -> SQL Connection Failure");
		}

		try
		{
			if(c.isValid(1))
			{
				l(id + " -> JDBC driver is connected to " + sqlAddress + "/" + sqlDatabase + " as " + sqlUsername);
			}

			else
			{
				f(id + " -> JDBC driver failed to connect to database.");
				throw new SQLException(id + " -> SQL Connection Failure");
			}
		}

		catch(SQLException e)
		{
			L.ex(e);
			f(id + " -> SQLException: " + e.getMessage());
			f(id + " -> SQLState: " + e.getSQLState());
			f(id + " -> VendorError: " + e.getErrorCode());
			throw new SQLException(id + " -> SQL Connection Failure");
		}
		catch(Throwable e)
		{
			L.ex(e);
		}

		return c;
	}

	public void finished(Connection c)
	{
		if(pooling)
		{
			J.a(() ->
			{
				try
				{
					c.close();
				}

				catch(SQLException e)
				{
					e.printStackTrace();
				}
			});
		}
	}

	public boolean tableExists(String t)
	{
		return existingTables.contains(t);
	}

	public boolean exists(Object object, String string) throws SQLException
	{
		if(validate(object))
		{
			try
			{
				PreparedStatement exists = prepareExists(object, string);
				ResultSet r = exists.executeQuery();

				if(r.next())
				{
					return true;
				}

				return false;
			}

			catch(IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}

			return false;
		}

		return false;
	}

	public boolean set(Object object) throws SQLException
	{
		if(validate(object))
		{
			try
			{
				PreparedStatement exists = prepareExists(object);
				ResultSet r = exists.executeQuery();
				PreparedStatement p = null;

				if(r.next())
				{
					p = prepareUpdate(object);
				}

				else
				{
					p = prepareInsert(object);
				}

				p.executeUpdate();
				return true;
			}

			catch(IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}

			return false;
		}

		return false;
	}

	public boolean delete(Object object) throws SQLException
	{
		if(validate(object))
		{

			try
			{
				PreparedStatement drop = prepareDrop(object);
				drop.executeUpdate();
				return true;
			}

			catch(IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}

			return false;
		}

		return false;
	}

	public long getTableSize(Class<?> object) throws SQLException, IllegalArgumentException, IllegalAccessException
	{
		String table = object.getDeclaredAnnotation(Table.class).value();
		PreparedStatement exists = prepareCount(table);
		ResultSet r = exists.executeQuery();

		if(r.next())
		{
			return r.getLong(1);
		}

		return -1;
	}

	// TODO: Test
	@SuppressWarnings("unchecked")
	public <T> void getAllFieldsFor(Class<?> tclass, String field, String condition, Consumer<T> t, long chunkSize) throws SQLException, IllegalArgumentException, IllegalAccessException
	{
		long m = 0;
		long count = getTableSize(tclass);

		while(m < count)
		{
			PreparedStatement exists = prepareGetAllFor(tclass, field, condition, m, chunkSize);
			m += chunkSize;
			ResultSet r = exists.executeQuery();

			while(r.next())
			{
				t.accept((T) r.getObject(1));
			}
		}
	}

	// TODO: Test
	public <T> void getAllFor(Supplier<T> tsup, String condition, Consumer<T> c, long chunkSize) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		T to = tsup.get();
		@SuppressWarnings("unchecked")
		Class<T> tclass = (Class<T>) to.getClass();
		long m = 0;
		long count = getTableSize(tclass);

		while(m < count)
		{
			PreparedStatement exists = prepareGetAllFor(tclass, condition, m, chunkSize);
			m += chunkSize;
			ResultSet r = exists.executeQuery();

			while(r.next())
			{
				T o = tsup.get();
				ingest(o, r);
				c.accept(o);
			}
		}
	}

	// TODO: Test
	public <T> T get(Class<T> t, Object idx) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException
	{
		T o = t.getConstructor(idx.getClass()).newInstance(idx);

		if(!get(o))
		{
			return null;
		}

		return o;
	}

	@SuppressWarnings("unchecked")
	public <T> T getTableField(Object idh, String field) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		if(validate(idh))
		{
			PreparedStatement exists = prepareGetSingleton(idh, field);
			ResultSet r = exists.executeQuery();

			if(r.next())
			{
				return (T) r.getObject(1);
			}
		}

		return null;
	}

	public <T> T get(Class<T> t, Object... params) throws Throwable
	{
		GList<Class<?>> g = new GList<>();
		for(Object i : params)
		{
			g.add(i.getClass());
		}

		@SuppressWarnings("unchecked")
		T tx = (T) t.getClass().getConstructor(g.toArray(new Class<?>[g.size()])).newInstance(params);
		get(tx);

		return tx;
	}

	public boolean get(Object object) throws SQLException
	{
		if(validate(object))
		{
			try
			{
				PreparedStatement exists = prepareGet(object);
				ResultSet r = exists.executeQuery();

				if(r.next())
				{
					ingest(object, r);

					return true;
				}
			}

			catch(IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}

			return false;
		}

		return false;
	}

	public boolean getWhere(Object object, String col, String is) throws SQLException
	{
		if(validate(object))
		{
			try
			{
				PreparedStatement exists = prepareGetWhere(object, col, is);
				ResultSet r = exists.executeQuery();

				if(r.next())
				{
					ingest(object, r);

					return true;
				}
			}

			catch(IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}

			return false;
		}

		return false;
	}

	public boolean getWhere2(Object object, String col, String is, String col2, String is2) throws SQLException
	{
		if(validate(object))
		{
			try
			{
				PreparedStatement exists = prepareGetWhere2(object, col, is, col2, is2);
				ResultSet r = exists.executeQuery();

				if(r.next())
				{
					ingest(object, r);

					return true;
				}
			}

			catch(IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}

			return false;
		}

		return false;
	}

	private void ingest(Object object, ResultSet r) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);
			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);
				if(i.getType().equals(UUID.class))
				{
					i.set(object, UUID.fromString(r.getString(c.name())));
				}

				else if(i.getType().equals(String.class))
				{
					i.set(object, r.getString(c.name()));
				}

				else if(i.getType().equals(Integer.class) || i.getType().equals(int.class))
				{
					i.set(object, r.getInt(c.name()));
				}

				else if(i.getType().equals(Double.class) || i.getType().equals(double.class))
				{
					i.set(object, r.getDouble(c.name()));
				}

				else if(i.getType().equals(Long.class) || i.getType().equals(long.class))
				{
					i.set(object, r.getLong(c.name()));
				}

				else if(i.getType().equals(Date.class))
				{
					i.set(object, r.getDate(c.name()));
				}

				else
				{
					System.out.println("Cannot handle type injection from table: " + i.getType().toString());
				}
			}
		}
	}

	public boolean validate(Object object) throws SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		if(existingTables.contains(table))
		{
			return true;
		}
		try
		{
			PreparedStatement exists = prepareCreateTableIfNotExists(object);
			exists.executeUpdate();
			PreparedStatement columns = prepareShowColumns(object);
			ResultSet set = columns.executeQuery();
			GList<String> cols = new GList<String>();

			while(set.next())
			{
				cols.add(set.getString("Field"));
			}

			GMap<String, Field> mcols = getFieldsFor(object);
			GList<String> add = new GList<String>();
			GList<String> rem = new GList<String>();

			for(String i : mcols.k())
			{
				if(!cols.contains(i))
				{
					add.add(i);
				}
			}

			for(String i : cols)
			{
				if(!mcols.containsKey(i))
				{
					rem.add(i);
				}
			}

			GList<String> alter = new GList<String>();
			GMap<String, Field> mcolsx = new GMap<String, Field>();

			for(String i : add)
			{
				mcolsx.put(i, mcols.get(i));
			}

			alter.addAll(getAdd(mcolsx));
			alter.addAll(getRem(rem));

			if(!alter.isEmpty())
			{
				PreparedStatement ps = prepareAlter(object, alter);
				ps.executeUpdate();
			}

			existingTables.add(table);

			return true;
		}

		catch(IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	private GMap<String, Field> getFieldsFor(Object object)
	{
		GMap<String, Field> f = new GMap<String, Field>();

		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);

			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);
				f.put(c.name(), i);
			}
		}

		return f;
	}

	private PreparedStatement prepareCreateTableIfNotExists(Object object) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String def = getTableDef(object);
		String sql = "CREATE TABLE IF NOT EXISTS `" + table + "` " + def + ";";
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private void l(String string)
	{
		if(logging)
		{
			L.i(string);
		}
	}

	public PreparedStatement prepareFindGive(Class<?> clazz, String find, String give, String equalsFind) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = clazz.getDeclaredAnnotation(Table.class).value();
		String sql = "SELECT `" + give + "` FROM `" + table + "` WHERE `" + find + "` = '" + equalsFind + "';";
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	public PreparedStatement prepareAlter(Object object, GList<String> alter) throws SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String def = alter.toString(", ");
		String sql = "ALTER TABLE `" + table + "` " + def + ";";
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareShowColumns(Object object) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String sql = "SHOW COLUMNS FROM `" + table + "`";
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareDrop(Object object) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String ex = "WHERE `" + getPrimary(object) + "` = '" + getPrimaryValue(object) + "'";
		String sql = "DELETE FROM `" + table + "` " + ex;
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareExists(Object object) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String ex = "WHERE `" + getPrimary(object) + "` = '" + getPrimaryValue(object) + "'";
		validate(object);
		String sql = "SELECT * FROM `" + table + "` " + ex;
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareGetAllFor(Object object, String condition, long m, long size) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String sql = "SELECT * FROM `" + table + "` WHERE " + condition + " LIMIT " + m + "," + size + ";";
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareGetAllFor(Class<?> c, String field, String condition, long m, long size) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = c.getDeclaredAnnotation(Table.class).value();
		String sql = "SELECT `" + field + "` FROM `" + table + "` WHERE " + condition + " LIMIT " + m + "," + size + ";";
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareExists(Object object, String column) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String ex = "WHERE `" + column + "` = '" + getValue(object, column) + "'";
		String sql = "SELECT * FROM `" + table + "` " + ex;
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareGetSingleton(Object object, String field) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String ex = "WHERE `" + getPrimary(object) + "` = '" + getPrimaryValue(object) + "'";
		String sql = "SELECT " + getField(object, field) + " FROM `" + table + "` " + ex;
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareCount(String table) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String sql = "SELECT COUNT(*) FROM `" + table + "`";
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareGet(Object object) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String ex = "WHERE `" + getPrimary(object) + "` = '" + getPrimaryValue(object) + "'";
		String sql = "SELECT " + getFieldsSelect(object) + " FROM `" + table + "` " + ex;
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareGetWhere(Object object, String field, String val) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String ex = "WHERE `" + field + "` = '" + val + "'";
		String sql = "SELECT " + getFieldsSelect(object) + " FROM `" + table + "` " + ex;
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareGetWhere2(Object object, String field, String val, String field2, String val2) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String ex = "WHERE `" + field + "` = '" + val + "' AND `" + field2 + "` = '" + val2 + "'";
		String sql = "SELECT " + getFieldsSelect(object) + " FROM `" + table + "` " + ex;
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareInsert(Object object) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String fields = getFields(object);
		String values = getValues(object);
		String sql = "INSERT INTO `" + table + "` " + fields + " VALUES " + values + ";";
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private PreparedStatement prepareUpdate(Object object) throws IllegalArgumentException, IllegalAccessException, SQLException
	{
		String table = object.getClass().getDeclaredAnnotation(Table.class).value();
		String updates = getFieldUpdates(object);
		String sql = "UPDATE `" + table + "` SET " + updates + ";";
		l("SQL -> " + sql);
		return getConnection().prepareStatement(sql);
	}

	private String getFieldUpdates(Object object) throws IllegalArgumentException, IllegalAccessException
	{
		GList<String> f = new GList<String>();
		String primary = "ERROR";
		String primaryValue = "ERROR";

		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);

			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);
				Object o = i.get(object);
				String value = c.placeholder();

				if(o != null)
				{
					value = sqlToString(o);
				}

				if(c.primary())
				{
					primary = c.name();
					primaryValue = value;
				}

				f.add("`" + c.name() + "` = '" + SQLTools.escapeString(value, true) + "'");
			}
		}

		return f.toString(", ") + " WHERE `" + primary + "` = '" + primaryValue + "'";
	}

	private String getFields(Object object)
	{
		GList<String> f = new GList<String>();

		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);

			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);
				f.add("`" + c.name() + "`");
			}
		}

		return "(" + f.toString(", ") + ")";
	}

	private String getTableDef(Object object)
	{
		GList<String> f = new GList<String>();
		String prim = "ERROR";
		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);

			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);
				if(c.primary())
				{
					prim = "(`" + c.name() + "`)";
				}

				if(!c.type().equals("TEXT"))
				{
					f.add("`" + c.name() + "`  " + c.type() + " NOT NULL DEFAULT '" + SQLTools.escapeString(c.placeholder(), true) + "'");
				}

				else
				{
					f.add("`" + c.name() + "`  " + c.type() + " NOT NULL");
				}
			}
		}

		f.add("PRIMARY KEY " + prim);

		return "(" + f.toString(", ") + ")";
	}

	private GList<String> getAdd(GMap<String, Field> f)
	{
		GList<String> alt = new GList<String>();

		for(String i : f.k())
		{
			Column c = f.get(i).getAnnotation(Column.class);
			alt.add("ADD `" + i + "` " + c.type() + " NOT NULL " + (c.type().equals("TEXT") ? "" : ("DEFAULT '" + c.placeholder() + "'")));
		}

		return alt;
	}

	private GList<String> getRem(GList<String> f)
	{
		GList<String> alt = new GList<String>();

		for(String i : f)
		{
			alt.add("DROP `" + i + "`");
		}

		return alt;
	}

	private String getFieldsSelect(Object object)
	{
		GList<String> f = new GList<String>();

		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);

			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);
				f.add("`" + c.name() + "`");
			}
		}

		return "" + f.toString(", ") + "";
	}

	private String getField(Object object, String varName)
	{
		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);

			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);
				if(i.getName().equals(varName) || c.name().equals(varName))
				{
					return c.name();
				}
			}
		}

		return varName;
	}

	private String getPrimary(Object object)
	{
		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);

			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);
				if(c.primary())
				{
					return c.name();
				}
			}
		}

		return null;
	}

	private String getValue(Object object, String column) throws IllegalArgumentException, IllegalAccessException
	{
		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);

			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);

				if(c.name().equals(column))
				{
					Object o = i.get(object);

					if(o == null)
					{
						return c.placeholder();
					}

					return sqlToString(o);
				}
			}
		}

		return null;
	}

	private String getPrimaryValue(Object object) throws IllegalArgumentException, IllegalAccessException
	{
		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);

			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);

				if(c.primary())
				{
					Object o = i.get(object);

					if(o == null)
					{
						return c.placeholder();
					}

					return sqlToString(o);
				}
			}
		}

		return null;
	}

	private String getValues(Object object) throws IllegalArgumentException, IllegalAccessException
	{
		GList<String> f = new GList<String>();

		for(Field i : object.getClass().getDeclaredFields())
		{
			i.setAccessible(true);

			if(i.isAnnotationPresent(Column.class))
			{
				Column c = i.getAnnotation(Column.class);
				Object o = i.get(object);
				String value = c.placeholder();

				if(o != null)
				{
					value = sqlToString(o);
				}

				f.add("'" + SQLTools.escapeString(value, true) + "'");
			}
		}

		return "(" + f.toString(", ") + ")";
	}

	private String sqlToString(Object o)
	{
		return o.toString();
	}

	public boolean has(String table, String column, String test) throws SQLException
	{
		PreparedStatement s = getConnection().prepareStatement("SELECT `" + column + "` FROM `" + table + "` WHERE `" + column + "` = '" + test + "'");
		ResultSet r = s.executeQuery();
		return r.next();
	}

	public Connection getConnection()
	{
		try
		{
			testConnection();
		}

		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return sql.get();
	}

	private void testConnection() throws SQLException
	{
		if(M.ms() - lastTest < 5000)
		{
			return;
		}

		lastTest = M.ms();

		try
		{
			if(!sql.get().isValid(3000))
			{
				throw new SQLException("Failed to connect");
			}
		}

		catch(Throwable e)
		{
			throw new SQLException("Failed to connect");
		}
	}

	private void f(String string)
	{
		L.f(string);
	}

	public <T> GList<T> getAllFor(String find, String inColumn, Class<T> clazz, GList<T> f, Supplier<T> s) throws SQLException
	{
		validate(s.get());
		String ss = "SELECT * FROM `" + clazz.getAnnotation(Table.class).value() + "` WHERE `" + inColumn + "` = '" + find + "'";
		l("SQL -> " + ss);
		PreparedStatement ps = getConnection().prepareStatement(ss);
		ResultSet r = ps.executeQuery();

		while(r.next())
		{
			T t = s.get();

			try
			{
				for(Field i : clazz.getDeclaredFields())
				{
					i.setAccessible(true);
					if(i.isAnnotationPresent(Column.class))
					{
						Column c = i.getAnnotation(Column.class);
						if(i.getType().equals(UUID.class))
						{
							i.set(t, UUID.fromString(r.getString(c.name())));
						}

						else if(i.getType().equals(String.class))
						{
							i.set(t, r.getString(c.name()));
						}

						else if(i.getType().equals(Integer.class) || i.getType().equals(int.class))
						{
							i.set(t, r.getInt(c.name()));
						}

						else if(i.getType().equals(Double.class) || i.getType().equals(double.class))
						{
							i.set(t, r.getDouble(c.name()));
						}

						else if(i.getType().equals(Long.class) || i.getType().equals(long.class))
						{
							i.set(t, r.getLong(c.name()));
						}

						else if(i.getType().equals(Date.class))
						{
							i.set(t, r.getDate(c.name()));
						}

						else
						{
							w("Cannot handle type injection from table: " + i.getType().toString());
						}
					}
				}

				f.add(t);
			}

			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}

		return f;
	}

	public <T> GList<T> getAllFor(Class<T> clazz, GList<T> f, Supplier<T> s) throws SQLException
	{
		validate(s.get());
		String ss = "SELECT * FROM `" + clazz.getAnnotation(Table.class).value() + "`";
		l("SQL -> " + ss);
		PreparedStatement ps = getConnection().prepareStatement(ss);
		ResultSet r = ps.executeQuery();

		while(r.next())
		{
			T t = s.get();

			try
			{
				for(Field i : clazz.getDeclaredFields())
				{
					i.setAccessible(true);
					if(i.isAnnotationPresent(Column.class))
					{
						Column c = i.getAnnotation(Column.class);
						if(i.getType().equals(UUID.class))
						{
							i.set(t, UUID.fromString(r.getString(c.name())));
						}

						else if(i.getType().equals(String.class))
						{
							i.set(t, r.getString(c.name()));
						}

						else if(i.getType().equals(Integer.class) || i.getType().equals(int.class))
						{
							i.set(t, r.getInt(c.name()));
						}

						else if(i.getType().equals(Double.class) || i.getType().equals(double.class))
						{
							i.set(t, r.getDouble(c.name()));
						}

						else if(i.getType().equals(Long.class) || i.getType().equals(long.class))
						{
							i.set(t, r.getLong(c.name()));
						}

						else if(i.getType().equals(Date.class))
						{
							i.set(t, r.getDate(c.name()));
						}

						else
						{
							w("Cannot handle type injection from table: " + i.getType().toString());
						}
					}
				}

				f.add(t);
			}

			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}

		return f;
	}

	private void w(String string)
	{
		L.w(string);
	}

	public void setConnectionWait(long databaseConnectionWait)
	{
		this.cwait = databaseConnectionWait;
	}

	public void setDriver(String driver)
	{
		this.driver = driver;
	}
}
