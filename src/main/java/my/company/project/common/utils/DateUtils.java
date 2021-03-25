package my.company.project.common.utils;

import my.company.project.common.log.Logger;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class DateUtils {

    private final Logger log = new Logger(this.getClass());

    public final String DEFAULT_PATTERN = DATE_PATTERN_11;

    public static final String DATE_PATTERN_1 = "dd-MM-yy";        /*	31-01-12*/
    public static final String DATE_PATTERN_2 = "dd-MM-yyyy";        /*	31-01-2012*/
    public static final String DATE_PATTERN_3 = "MM-dd-yyyy";        /*	01-31-2012*/
    public static final String DATE_PATTERN_4 = "yyyy-MM-dd";        /*		2012-01-31*/
    public static final String DATE_PATTERN_5 = "yyyy-MM-dd HH:mm:ss";        /*		2012-01-31 23:59:59*/
    public static final String DATE_PATTERN_6 = "yyyy-MM-dd HH:mm:ss.SSS";        /*			2012-01-31 23:59:59.999*/
    public static final String DATE_PATTERN_7 = "yyyy-MM-dd HH:mm:ss.SSSZ";        /*			2012-01-31 23:59:59.999+0100*/
    public static final String DATE_PATTERN_8 = "EEEEE MMMMM yyyy HH:mm:ss.SSSZ";    /*	Saturday November 2012 10:45:42.720+0100*/
    public static final String DATE_PATTERN_9 = "EEE MMM dd HH:mm:ss zzz yyyy";    /*	Sat Nov 01 10:45:42 UTC 2019*/
    public static final String DATE_PATTERN_10 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";/*2001-09-11T20:20:30.000Z*/
    public static final String DATE_PATTERN_11 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_PATTERN_12 = "EEE, dd MMM yyyy HH:mm:ss zzz"; // Mon, 01 Jan 2018 11:01:36 GMT
    public static final String DATE_PATTERN_13 = "dd-MMM-yyyy HH:mm:ss.SSS XXX"; // 20-JAN-2021 14:30:41.006 +00:00


    public long current() {
        return System.currentTimeMillis();
    }

    public long elapsed(long start) {
        return System.currentTimeMillis() - start;
    }


    /*

    public static void main(String[] args) {

        DateUtils dateUtils = new DateUtils();
        Date date = new Date();

        try {
            XMLGregorianCalendar xmlGregorianCalendar = dateUtils.convertWithFormat(date, DateUtils.DATE_PATTERN_11);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    */
    public XMLGregorianCalendar convertWithFormat(Date d, String format)
            throws DatatypeConfigurationException, ParseException {
        //2020-09-24T15:25:44.978+0200
        DateFormat dateFormat = new SimpleDateFormat(format);
        String format1 = dateFormat.format(d);
        XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        return xmlDate;
    }

    public XMLGregorianCalendar convert(Date yourDate) throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(yourDate);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        return date2;
    }

    public String getTimestampFormatted(String pattern) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(pattern);
        Date now = new Date();
        return sdfDate.format(now);
    }

    public String getTimestampFormatted(Date date, String pattern) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(pattern);
        return sdfDate.format(date);
    }

    public Date getDateFormatted(String date, String format) {
        log.info("Formatting date='{}' with format='{}'...", date, format);
        Date dateFormatted = null;

        if (format == null) {
            format = DEFAULT_PATTERN;
            log.error("The formatter string is null. The date '{}' will be formatted with the default pattern '{}'", date, format);
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);

        try {
            dateFormatted = formatter.parse(date);
        } catch (ParseException e) {
            log.error(
                    "Impossible to parse String [{}] to a date with format [{}]. The returned date will set to null...",
                    date, format);

            dateFormatted = null;
        }
        log.info("Formatting date='{}' with format='{}'... done!", date, format);
        return dateFormatted;

    }

    public Date getCurrentDate() {
        return new Date();
    }

    public Date getCurrentDateWithoutDaysMinutesSecondsMillis() {
        Date currentDate = getCurrentDate();
        return removeDaysMinutesSecondsMillis(currentDate);
    }

    public String getCurrentTimestampAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd-MM-yy-HH.mm.ss SSS");
        return sdf.format(new Date());
    }

    public String getCurrentTimestampAsString(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(pattern);
        return sdf.format(new Date());
    }

    public Date convert(Calendar calendar) {
        return calendar.getTime();
    }

    public String getDateAsString(Calendar calendar, String format) {
        Date date = convert(calendar);
        return getTimestampFormatted(date, format);
    }

    public String getDateAsString(Date date, String format) {
        return getTimestampFormatted(date, format);
    }

    public String fromTo(String date, String templateFrom, String templateTo) {
        Date dateFormatted = getDateFormatted(date, templateFrom);
        if (dateFormatted == null) {
            log.error("Impossible to format date={}", date);
            return null;
        } else {
            return getDateAsString(dateFormatted, templateTo);
        }
    }

    public String getDateAsString(Calendar calendar) {
        Date date = convert(calendar);
        return getTimestampFormatted(date, DATE_PATTERN_6);
    }

    public Date getClosestDate(Date date1, Date date2) {
        return (date1 == null || date1.before(date2)) ?
                date2 : date1;
    }

    public Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public Date addDays(Date date, String days) {
        int numberDays = Integer.parseInt(days);
        return addDays(date, numberDays);
    }

    public boolean isDateInRange(Date current, Date lowerRange, Date upperRange) {
        return lowerRange.compareTo(current) * current.compareTo(upperRange) >= 0;
    }

    public boolean isDateLowerThan(Date current, Date upperRange) {
        return current.compareTo(upperRange) < 0;
    }

    public boolean isDateLowerThanInclusive(Date current, Date upperRange) {
        return current.compareTo(upperRange) <= 0;
    }

    public boolean isDateUpperThan(Date current, Date upperRange) {
        return current.compareTo(upperRange) > 0;
    }

    public boolean isDateUpperThanInclusive(Date current, Date upperRange) {
        boolean b = current.compareTo(upperRange) >= 0;
        log.info("Is date '{}' greater than '{}' ? --> '{}'", current, upperRange, b);
        return b;
    }

    public Date removeDaysMinutesSecondsMillis(Date date) {
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time = cal.getTimeInMillis();
        return new Date(time);
    }

}
