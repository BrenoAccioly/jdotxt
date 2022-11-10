package com.todotxt.todotxttouch.task;

import com.chschmid.jdotxt.gui.JdotxtGUI;
import com.chschmid.jdotxt.util.LanguagesController;
import com.todotxt.todotxttouch.util.RelativeDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RelativeDateTest {
/*
    @Mock
    LanguagesController languagesController;

    @InjectMocks
    JdotxtGUI jdotxtGUI;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }
*/
    @Test
    public void testGetRelativeDate() {
        // JdotxtGUI.lang.getWord("dates_months_ago")
        //Mockito.when(languagesController.getWord(anyString())).thenReturn("");

        Calendar c1 = new GregorianCalendar(2022, Calendar.OCTOBER, 1);
        Calendar c2 = new GregorianCalendar(2022, Calendar.OCTOBER, 1);

        assertEquals(null, RelativeDate.getRelativeDate(c1, c2));

        c1 = new GregorianCalendar(2022, Calendar.OCTOBER, 1);
        c2 = new GregorianCalendar(2022, Calendar.OCTOBER, 3);

        assertEquals(null, RelativeDate.getRelativeDate(c2, c1));
    }

}
