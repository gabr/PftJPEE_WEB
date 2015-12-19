package pl.polsl.gabrys.arkadiusz.tagshandlers;

import java.util.Date;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Class implements DatePrinter Tag Handler
 * @author arkad_000
 * Version 1.0
 */
public class DatePrinter extends SimpleTagSupport {
    private Date date;

    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        try {
            JspFragment f = getJspBody();
            if (f != null) {
                f.invoke(out);
            }
            String year = Integer.toString(date.getYear() + 1900);
            String day = Integer.toString(date.getDay());
            String month = Integer.toString(date.getMonth());
            
            out.write(year + "." + month + "." + day);
        } catch (java.io.IOException ex) {
            throw new JspException("Error in DatePrinter tag", ex);
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
