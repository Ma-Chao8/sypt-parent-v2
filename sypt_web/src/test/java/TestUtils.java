import com.tianma315.core.exception.MessageException;
import com.tianma315.core.utils.MD5Utils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestUtils {
    @Test
    public void test() {

        System.out.println(parseDate("2019-7","yyyy-MM").get(Calendar.YEAR));
        System.out.println(parseDate("2019-7","yyyy-MM").get(Calendar.MONTH));
        System.out.println(parseDate("2019-7","yyyy-MM").get(Calendar.DAY_OF_MONTH));
        System.out.println(parseDate("2019-7","yyyy-MM").getMaximum(Calendar.DAY_OF_MONTH));

    }

    private Calendar parseDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.get(Calendar.YEAR);
        try {
            c.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new MessageException(String.format("日期格式错误，正确格式为%s", format));
        }
        return c;
    }

    @Test
    public void number() {
        System.out.println(String.format("%s.txt",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
    }

}
