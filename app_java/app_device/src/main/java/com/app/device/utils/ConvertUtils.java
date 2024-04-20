package com.app.device.utils;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class ConvertUtils

{
    public static final int UNASSIGNED_NULL = 0;
    public static final int ASSIGNED_NULL = 1;
    public static final int BYTE = 2;
    public static final int SHORT = 3;
    public static final int INT = 4;
    public static final int LONG = 5;
    public static final int FLOAT = 6;
    public static final int DOUBLE = 7;
    public static final int BIGINTEGER = 8;
    public static final int BIGDECIMAL = 10;
    public static final int BOOLEAN = 11;
    public static final int INPUTSTREAM = 12;
    public static final int DATE = 13;
    public static final int TIME = 14;
    public static final int TIMESTAMP = 15;
    public static final int STRING = 16;
    public static final int OBJECT = 17;
    public static final int BYTE_ARRAY = 18;
    public static final int BLOB = 19;
    public static final int CLOB = 20;
    public static final int ENUM = 21;
    public static final String ASSIGNEDNULL_S = "ASSIGNED_NULL";
    public static final String UNASSIGNEDNULL_S = "UNASSIGNED_NULL";
    public static final String BYTETYPE_S = "BYTE";
    public static final String SHORTTYPE_S = "SHORT";
    public static final String INTTYPE_S = "INT";
    public static final String INTEGERTYPE_S = "INTEGER";
    public static final String BIGINTEGERTYPE_S = "BIGINTEGER";
    public static final String LONGTYPE_S = "LONG";
    public static final String FLOATTYPE_S = "FLOAT";
    public static final String DOUBLETYPE_S = "DOUBLE";
    public static final String BIGDECIMALTYPE_S = "BIGDECIMAL";
    public static final String BOOLEANTYPE_S = "BOOLEAN";
    public static final String INPUTSTREAMTYPE_S = "INPUTSTREAM";
    public static final String DATETYPE_S = "DATE";
    public static final String TIMETYPE_S = "TIME";
    public static final String TIMESTAMPTYPE_S = "TIMESTAMP";
    public static final String BYTEARRAYTYPE_S = "[B";
    public static final String STRINGTYPE_S = "STRING";
    public static final String OBJECTTYPE_S = "OBJECT";
    public static final String BLOB_S = "SERIALBLOB";
    public static final String CLOB_S = "SERIALCLOB";
    public static final Double ZERODOUBLE = Double.valueOf(0.0D);
    public static final Float ZEROFLOAT = Float.valueOf(0.0F);
    public static final String ZEROSTRING = "0";
    public static final String FALSESTRING = "false";
    public static final String NOTSUPPORTTYPECAST = "不支持类型转换";
    public static final char PACKAGESEP = '.';
    public static final BigDecimal ZEROBIGDECIMAL = new BigDecimal(0);
    public static final byte[] EMPTYBYTES = new byte[0];
    public static final char[] EMPTYCHARS = new char[0];
    private static Map<String, String> types = new HashMap();
    static Map typeMap;

    public static int getType(Object obj)
    {
        if (obj == null) {
            return 1;
        }
        Class cls = obj.getClass();
        if (cls.isEnum()) {
            return 21;
        }
        return getType(obj.getClass());
    }

    public static String getClassSimpleName(Class cls)
    {
        String name = cls.getName();
        int index = name.lastIndexOf('.');
        if (index > 0) {
            name = name.substring(index + 1);
        }
        return name;
    }

    public static int getType(Class cls)
    {
        if (cls == null) {
            return 1;
        }
        if (cls.isEnum()) {
            return 21;
        }
        String typeValue = types.get(getClassSimpleName(cls).toUpperCase());
        if (typeValue != null) {
            return Integer.parseInt(typeValue);
        }
        return 17;
    }

    private static void typeProblem(int unexpectedType, int expectedType, boolean getter)
    {
        throw new CastException("E0208.0009", I18nUtil.getMessage("E0208.0009", "不支持内型转换"));
    }

    public static BigDecimal toBigDecimal(Object object)
    {
        int type = getType(object);
        switch (type)
        {
            case 21:
                return new BigDecimal(((Enum)object).ordinal());
            case 11:
                return new BigDecimal(((Boolean)object).booleanValue() ? 1 : 0);
            case 2:
                return new BigDecimal(((Byte)object).longValue());
            case 3:
                return new BigDecimal(((Short)object).longValue());
            case 4:
                return new BigDecimal(String.valueOf(object));
            case 8:
                return BigDecimal.valueOf(((BigInteger)object).longValue(), 0);
            case 5:
                return BigDecimal.valueOf(((Long)object).longValue(), 0);
            case 6:
                return new BigDecimal(String.valueOf(object));
            case 7:
                return new BigDecimal(String.valueOf(object));
            case 10:
                return (BigDecimal)object;
            case 14:
                return BigDecimal.valueOf(((Time)object).getTime());
            case 13:
                return BigDecimal.valueOf(((Date)object).getTime());
            case 15:
                return BigDecimal.valueOf(((Timestamp)object).getTime());
            case 0:
            case 1:
                return new BigDecimal(0);
            case 16:
                String stringVal = (String)object;
                return (stringVal == null) || (stringVal.length() == 0) ? ZEROBIGDECIMAL : new BigDecimal(stringVal);
        }
        typeProblem(type, 10, true);
        return null;
    }

    public static Boolean toBoolean(Object object)
    {
        int type = getType(object);
        switch (type)
        {
            case 11:
                return (Boolean)object;
            case 2:
                return Boolean.valueOf(((Byte)object).longValue() != 0L);
            case 3:
                return Boolean.valueOf(((Short)object).longValue() != 0L);
            case 4:
                return Boolean.valueOf(((Integer)object).intValue() != 0);
            case 8:
                return Boolean.valueOf(((BigInteger)object).intValue() != 0);
            case 5:
                return Boolean.valueOf(((Long)object).longValue() != 0L);
            case 6:
                return Boolean.valueOf(((Float)object).compareTo(ZEROFLOAT) != 0);
            case 7:
                return Boolean.valueOf(((Double)object).compareTo(ZERODOUBLE) != 0);
            case 10:
                return Boolean.valueOf(((BigDecimal)object).longValue() != 0L);
            case 14:
                return Boolean.valueOf(object != null);
            case 13:
                return Boolean.valueOf(object != null);
            case 15:
                return Boolean.valueOf(object != null);
            case 0:
            case 1:
                return Boolean.FALSE;
            case 16:
                String stringVal = (String)object;

                boolean tmpValue = (stringVal == null) || (stringVal.length() == 0) || ("0".equals(stringVal)) || ("false".equalsIgnoreCase(stringVal));
                return Boolean.valueOf(!tmpValue);
        }
        typeProblem(type, 11, true);
        return Boolean.FALSE;
    }

    public static Byte toByte(Object object)
    {
        int type = getType(object);
        switch (type)
        {
            case 11:
                return Byte.valueOf(Integer.valueOf(((Boolean)object).booleanValue() ? 1 : 0).byteValue());
            case 2:
                return (Byte)object;
            case 3:
                return Byte.valueOf(((Short)object).byteValue());
            case 4:
                return Byte.valueOf(((Integer)object).byteValue());
            case 8:
                return Byte.valueOf(((BigInteger)object).byteValue());
            case 5:
                return Byte.valueOf(((Long)object).byteValue());
            case 6:
                return Byte.valueOf(((Float)object).byteValue());
            case 7:
                return Byte.valueOf(((Double)object).byteValue());
            case 10:
                return Byte.valueOf(((BigDecimal)object).byteValue());
            case 14:
                return Byte.valueOf(Long.valueOf(((Time)object).getTime()).byteValue());
            case 13:
                return Byte.valueOf(Long.valueOf(((Date)object).getTime()).byteValue());
            case 15:
                return Byte.valueOf(Long.valueOf(((Timestamp)object).getTime()).byteValue());
            case 0:
            case 1:
                return null;
            case 16:
                String stringVal = (String)object;
                if (StringUtil.isNotEmptyString(stringVal)) {
                    try
                    {
                        return Byte.valueOf(stringVal.getBytes("UTF-8")[0]);
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
                return null;
        }
        typeProblem(type, 2, true);
        return null;
    }

    public static byte[] toByteArray(Object object)
    {
        return (byte[])object;
    }

    public static Date toDate(Object value)
    {
        if (value == null) {
            return null;
        }
        long time = 0L;
        if ((value instanceof Timestamp))
        {
            time = ((Timestamp)value).getTime();
        }
        else
        {
            if ((value instanceof Time)) {
                return (Time)value;
            }
            if ((value instanceof Date)) {
                return (Date)value;
            }
            if ((value instanceof Long)) {
                time = ((Long)value).longValue();
            } else if ((value instanceof String)) {
                time = new Date((String)value).getTime();
            } else {
                time = ((Date)value).getTime();
            }
        }
        return new Date(time);
    }

    public static java.sql.Date toSqlDate(Object value)
    {
        if (value == null) {
            return null;
        }
        long time = 0L;
        if ((value instanceof Timestamp)) {
            time = ((Timestamp)value).getTime();
        } else if ((value instanceof Time)) {
            time = ((Time)value).getTime();
        } else if ((value instanceof Date)) {
            time = ((Date)value).getTime();
        } else if ((value instanceof String)) {
            try
            {
                time = new SimpleDateFormat("yyyy-MM-dd").parse((String)value).getTime();
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        } else {
            time = ((Date)value).getTime();
        }
        return new java.sql.Date(time);
    }

    public static Double toDouble(Object object)
    {
        int type = getType(object);
        switch (type)
        {
            case 21:
                return Double.valueOf(((Enum)object).ordinal());
            case 11:
                return Double.valueOf(((Boolean)object).booleanValue() ? 1.0D : 0.0D);
            case 2:
                return Double.valueOf(((Byte)object).doubleValue());
            case 3:
                return Double.valueOf(((Short)object).doubleValue());
            case 4:
                return Double.valueOf(((Integer)object).doubleValue());
            case 8:
                return Double.valueOf(((BigInteger)object).doubleValue());
            case 5:
                return Double.valueOf(((Long)object).doubleValue());
            case 6:
                return Double.valueOf(((Float)object).doubleValue());
            case 7:
                return (Double)object;
            case 10:
                return Double.valueOf(((BigDecimal)object).doubleValue());
            case 14:
                return Double.valueOf(Long.valueOf(((Time)object).getTime()).doubleValue());
            case 13:
                return Double.valueOf(Long.valueOf(((Date)object).getTime()).doubleValue());
            case 15:
                return Double.valueOf(Long.valueOf(((Timestamp)object).getTime()).doubleValue());
            case 0:
            case 1:
                return null;
            case 16:
                String stringVal = (String)object;
                if (StringUtil.isNotEmptyString(stringVal)) {
                    return Double.valueOf(stringVal);
                }
                return null;
        }
        typeProblem(type, 7, true);
        return null;
    }

    public static Float toFloat(Object object)
    {
        int type = getType(object);
        switch (type)
        {
            case 21:
                return Float.valueOf(((Enum)object).ordinal());
            case 11:
                return Float.valueOf(((Boolean)object).booleanValue() ? 1.0F : 0.0F);
            case 2:
                return Float.valueOf(((Byte)object).floatValue());
            case 3:
                return Float.valueOf(((Short)object).floatValue());
            case 8:
                return Float.valueOf(((BigInteger)object).floatValue());
            case 4:
                return Float.valueOf(((Integer)object).floatValue());
            case 5:
                return Float.valueOf(((Long)object).floatValue());
            case 6:
                return (Float)object;
            case 7:
                return Float.valueOf(((Double)object).floatValue());
            case 10:
                return Float.valueOf(((BigDecimal)object).floatValue());
            case 14:
                return Float.valueOf((float)((Time)object).getTime());
            case 13:
                return Float.valueOf((float)((Date)object).getTime());
            case 15:
                return Float.valueOf((float)((Timestamp)object).getTime());
            case 0:
            case 1:
                return null;
            case 16:
                String stringVal = (String)object;
                if (StringUtil.isNotEmptyString(stringVal)) {
                    return Float.valueOf(stringVal);
                }
                return null;
        }
        typeProblem(type, 6, true);
        return null;
    }

    public static InputStream toInputStream(Object object)
    {
        return (InputStream)object;
    }

    public static Integer toInteger(Object object)
    {
        int type = getType(object);
        switch (type)
        {
            case 21:
                return Integer.valueOf(((Enum)object).ordinal());
            case 11:
                return Integer.valueOf(((Boolean)object).booleanValue() ? 1 : 0);
            case 2:
                return Integer.valueOf(((Byte)object).intValue());
            case 3:
                return Integer.valueOf(((Short)object).intValue());
            case 4:
                return (Integer)object;
            case 8:
                return Integer.valueOf(((BigInteger)object).intValue());
            case 5:
                return Integer.valueOf(((Long)object).intValue());
            case 6:
                return Integer.valueOf(((Float)object).intValue());
            case 7:
                return Integer.valueOf(((Double)object).intValue());
            case 10:
                return Integer.valueOf(((BigDecimal)object).intValue());
            case 14:
                return Integer.valueOf((int)((Time)object).getTime());
            case 13:
                return Integer.valueOf((int)((Date)object).getTime());
            case 15:
                return Integer.valueOf((int)((Timestamp)object).getTime());
            case 0:
            case 1:
                return null;
            case 16:
                String stringVal = (String)object;
                if (StringUtil.isNotEmptyString(stringVal)) {
                    return Integer.valueOf(stringVal);
                }
                return Integer.valueOf(0);
        }
        typeProblem(type, 4, true);
        return null;
    }

    public static int toInt(Object object)
    {
        int type = getType(object);
        switch (type)
        {
            case 21:
                return ((Enum)object).ordinal();
            case 11:
                return ((Boolean)object).booleanValue() ? 1 : 0;
            case 2:
                return ((Byte)object).intValue();
            case 3:
                return ((Short)object).intValue();
            case 4:
                return ((Integer)object).intValue();
            case 8:
                return ((BigInteger)object).intValue();
            case 5:
                return ((Long)object).intValue();
            case 6:
                return ((Float)object).intValue();
            case 7:
                return ((Double)object).intValue();
            case 10:
                return ((BigDecimal)object).intValue();
            case 14:
                return (int)((Time)object).getTime();
            case 13:
                return (int)((Date)object).getTime();
            case 15:
                return (int)((Timestamp)object).getTime();
            case 0:
            case 1:
                return 0;
            case 16:
                String stringVal = (String)object;
                if (StringUtil.isEmptyString(stringVal)) {
                    return 0;
                }
                return Integer.parseInt(stringVal);
        }
        typeProblem(type, 4, true);
        return 0;
    }

    public static Long toLong(Object object)
    {
        int type = getType(object);
        switch (type)
        {
            case 2:
                return Long.valueOf(((Byte)object).longValue());
            case 3:
                return Long.valueOf(((Short)object).longValue());
            case 4:
                return Long.valueOf(((Integer)object).longValue());
            case 8:
                return Long.valueOf(((BigInteger)object).longValue());
            case 5:
                return (Long)object;
            case 6:
                return Long.valueOf(((Float)object).longValue());
            case 7:
                return Long.valueOf(((Double)object).longValue());
            case 10:
                return Long.valueOf(((BigDecimal)object).longValue());
            case 14:
                return Long.valueOf(((Time)object).getTime());
            case 13:
                return Long.valueOf(((Date)object).getTime());
            case 15:
                return Long.valueOf(((Timestamp)object).getTime());
            case 0:
            case 1:
                return null;
            case 16:
                String stringVal = (String)object;
                if (StringUtil.isNotEmptyString(stringVal)) {
                    return Long.valueOf(stringVal);
                }
                return null;
        }
        typeProblem(type, 5, true);
        return null;
    }

    public static Short toShort(Object object)
    {
        int type = getType(object);
        switch (type)
        {
            case 2:
                return Short.valueOf(((Byte)object).shortValue());
            case 3:
                return (Short)object;
            case 4:
                return Short.valueOf(((Integer)object).shortValue());
            case 8:
                return Short.valueOf(((BigInteger)object).shortValue());
            case 5:
                return Short.valueOf(((Long)object).shortValue());
            case 6:
                return Short.valueOf(((Float)object).shortValue());
            case 7:
                return Short.valueOf(((Double)object).shortValue());
            case 10:
                return Short.valueOf(((BigDecimal)object).shortValue());
            case 14:
                return Short.valueOf((short)(int)((Time)object).getTime());
            case 13:
                return Short.valueOf((short)(int)((Date)object).getTime());
            case 15:
                return Short.valueOf((short)(int)((Timestamp)object).getTime());
            case 0:
            case 1:
                return null;
            case 16:
                String stringVal = (String)object;
                if (StringUtil.isNotEmptyString(stringVal)) {
                    return Short.valueOf(stringVal);
                }
                return null;
        }
        typeProblem(type, 5, true);
        return null;
    }

    public static String toString(Object object)
    {
        if (object == null) {
            return null;
        }
        return String.valueOf(object);
    }

    public static Timestamp toTimestamp(Object value)
    {
        if (value == null) {
            return null;
        }
        long time = 0L;
        if ((value instanceof Timestamp)) {
            return (Timestamp)value;
        }
        if ((value instanceof Time)) {
            time = ((Time)value).getTime();
        } else if ((value instanceof Date)) {
            time = ((Date)value).getTime();
        } else if ((value instanceof String)) {
            time = new Date((String)value).getTime();
        } else {
            time = ((Date)value).getTime();
        }
        return new Timestamp(time);
    }


    public static Time toTime(Object value)
    {
        if (value == null) {
            return null;
        }
        long time = 0L;
        if ((value instanceof Timestamp))
        {
            time = ((Timestamp)value).getTime();
        }
        else if ((value instanceof Date))
        {
            time = ((Date)value).getTime();
        }
        else
        {
            if ((value instanceof Time)) {
                return (Time)value;
            }
            if ((value instanceof String)) {
                time = new Date((String)value).getTime();
            } else {
                time = ((Date)value).getTime();
            }
        }
        return new Time(time);
    }



    public static Class sqlTypeToJavaType(int sqlType)
    {
        Class result = null;
        switch (sqlType)
        {
            case 12:
                result = String.class;
                break;
            case 1:
                result = String.class;
                break;
            case -5:
                result = BigInteger.class;
                break;
            case 2004:
                result = SerialBlob.class;
                break;
            case 2005:
                result = SerialClob.class;
                break;
            case 16:
                result = Boolean.class;
                break;
            case 2:
            case 3:
            case 6:
            case 8:
                result = BigDecimal.class;
                break;
            case 4:
                result = Integer.class;
                break;
            case 91:
                result = Date.class;
                break;
            case 92:
                result = Time.class;
                break;
            case 93:
                result = Timestamp.class;
                break;
            default:
                result = String.class;
        }
        return result;
    }

    public static String getObjectToString(Object object)
    {
        if (object == null) {
            return null;
        }
        Class<?> cls = object.getClass();
        if ((cls == Short.TYPE) || (cls == Short.class) || (cls == Integer.TYPE) || (cls == Integer.class)) {
            return String.valueOf(object);
        }
        if ((cls == Long.TYPE) || (cls == Long.class) || (cls == Double.TYPE) || (cls == Double.class)) {
            return String.valueOf(object);
        }
        if ((cls == Float.TYPE) || (cls == Float.class) || (cls == BigDecimal.class)) {
            return String.valueOf(object);
        }
        if ((cls == BigInteger.class) || (cls == Boolean.TYPE) || (cls == Boolean.class)) {
            return String.valueOf(object);
        }
        if ((cls == Byte.TYPE) || (cls == Byte.class) || (cls == String.class)) {
            return String.valueOf(object);
        }
        if (cls == Date.class) {
            return String.valueOf(((Date)object).getTime());
        }
        if (cls == java.sql.Date.class) {
            return String.valueOf(((java.sql.Date)object).getTime());
        }
        if (cls == Timestamp.class) {
            return String.valueOf(((Timestamp)object).getTime());
        }
        return null;
    }

    public static Object getObject(String type, String value)
    {
        if (StringUtil.isNotEmptyString(type))
        {
            if (Boolean.class.getName().toUpperCase().equals(type.toUpperCase())) {
                return Boolean.valueOf(value);
            }
            if (Integer.class.getName().toUpperCase().equals(type.toUpperCase())) {
                return Integer.valueOf(value);
            }
            if (BigDecimal.class.getName().toUpperCase().equals(type.toUpperCase())) {
                return new BigDecimal(value);
            }
            if (Date.class.getName().toUpperCase().equals(type.toUpperCase())) {
                return new Date(value);
            }
            if (java.sql.Date.class.getName().toUpperCase().equals(type.toUpperCase())) {
                return new java.sql.Date(new Date(value).getTime());
            }
            if (Timestamp.class.getName().toUpperCase().equals(type.toUpperCase())) {
                return Timestamp.valueOf(value);
            }
            if (String.class.getName().toUpperCase().equals(type.toUpperCase())) {
                return value;
            }
        }
        return null;
    }

    public static boolean checkComparable(Object object)
    {
        return (object != null) && (Comparable.class.isAssignableFrom(object.getClass()));
    }

    public static boolean checkCollection(Object object)
    {
        return (object != null) && (Collection.class.isAssignableFrom(object.getClass()));
    }

    static
    {
        types.put("BIGDECIMAL", String.valueOf(10));
        types.put("BOOLEAN", String.valueOf(11));
        types.put("[B", String.valueOf(18));
        types.put("BYTE", String.valueOf(2));
        types.put("DATE", String.valueOf(13));
        types.put("DOUBLE", String.valueOf(7));
        types.put("FLOAT", String.valueOf(6));
        types.put("INPUTSTREAM", String.valueOf(12));
        types.put("INT", String.valueOf(4));
        types.put("INTEGER", String.valueOf(4));
        types.put("BIGINTEGER", String.valueOf(8));
        types.put("LONG", String.valueOf(5));
        types.put("OBJECT", String.valueOf(17));
        types.put("SHORT", String.valueOf(3));
        types.put("STRING", String.valueOf(16));
        types.put("TIMESTAMP", String.valueOf(15));
        types.put("TIME", String.valueOf(14));
        types.put("SERIALBLOB", String.valueOf(19));
        types.put("SERIALCLOB", String.valueOf(20));

        typeMap = new HashMap();

        typeMap.put(String.class, Integer.valueOf(12));
        typeMap.put(BigInteger.class, Integer.valueOf(-5));
        typeMap.put(SerialBlob.class, Integer.valueOf(2004));
        typeMap.put(SerialClob.class, Integer.valueOf(2005));
        typeMap.put(Boolean.class, Integer.valueOf(16));
        typeMap.put(Boolean.TYPE, Integer.valueOf(16));
        Integer number = Integer.valueOf(2);
        typeMap.put(Float.class, number);
        typeMap.put(Float.TYPE, number);
        typeMap.put(Double.class, number);
        typeMap.put(Double.TYPE, number);
        typeMap.put(BigDecimal.class, number);
        typeMap.put(Long.TYPE, number);
        typeMap.put(Long.class, number);

        typeMap.put(Integer.class, Integer.valueOf(4));
        typeMap.put(Integer.TYPE, Integer.valueOf(4));

        typeMap.put(java.sql.Date.class, Integer.valueOf(91));
        typeMap.put(Date.class, Integer.valueOf(91));
        typeMap.put(Date.class, Integer.valueOf(93));
        typeMap.put(Time.class, Integer.valueOf(92));
        typeMap.put(Timestamp.class, Integer.valueOf(93));
    }

    public static int javaTypeToSqlType(Class cls)
    {
        if (cls.isEnum()) {
            return 4;
        }
        Integer result = (Integer)typeMap.get(cls);
        if (result == null) {
            return 12;
        }
        return result.intValue();
    }


}





