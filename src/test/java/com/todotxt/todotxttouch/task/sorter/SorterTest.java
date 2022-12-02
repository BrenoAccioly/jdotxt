package com.todotxt.todotxttouch.task.sorter;

import com.todotxt.todotxttouch.task.Task;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class SorterTest {


    @Test
    public void testEqualLists1(){
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        l1.add(1);
        l2.add(1);
        assertEquals(Sorters.compareLists(l1,l2),0);
    }

    @Test
    public void testEqualLists2(){
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        l1.add(1);
        l1.add(2);
        l2.add(1);
        assertEquals(Sorters.compareLists(l1,l2),1);
    }

    @Test
    public void testEqualLists3(){
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        l1.add(1);
        l2.add(1);
        l2.add(2);
        assertEquals(Sorters.compareLists(l1,l2),-1);
    }

    @Test
    public void testEqualListsWithNullElement1(){
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        l1.add(null);
        l2.add(null);
        assertEquals(Sorters.compareLists(l1,l2),0);
    }

    @Test
    public void testEqualListsWithNullElement2(){
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        l1.add(null);
        l2.add(null);
        l2.add(2);
        assertEquals(Sorters.compareLists(l1,l2),-1);
    }

    @Test
    public void testEqualListsWithNullElement3(){
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        l1.add(null);
        l2.add(1);
        assertEquals(Sorters.compareLists(l1,l2),-1);
    }

    @Test
    public void testCompareNullDates(){
        Date d1 = Date.from(Instant.ofEpochMilli(Date.UTC(2000,01,01,0,0,0)));
        Date d2 = Date.from(Instant.ofEpochMilli(Date.UTC(2001,01,01,0,0,0)));
        assertEquals(Sorters.compareDates(null,null,false),0);
    }

    @Test
    public void testCompareDatesAscending() {
        Date d1 = Date.from(Instant.ofEpochMilli(Date.UTC(2000,01,01,0,0,0)));
        Date d2 = Date.from(Instant.ofEpochMilli(Date.UTC(2001,01,01,0,0,0)));
        assertEquals(Sorters.compareDates(d1,d2,true),-1);
    }

    @Test
    public void testCompareDatesDescending() {
        Date d1 = Date.from(Instant.ofEpochMilli(Date.UTC(2000,01,01,0,0,0)));
        Date d2 = Date.from(Instant.ofEpochMilli(Date.UTC(2001,01,01,0,0,0)));
        assertEquals(Sorters.compareDates(d1,d2,false),1);
    }

    @Test
    public void testCompareDatesWithLHSNull() {
        Date d2 = Date.from(Instant.ofEpochMilli(Date.UTC(2001,01,01,0,0,0)));
        assertEquals(Sorters.compareDates(null,d2,false),-1);
    }

    @Test
    public void testCompareDatesWithRHSNull() {
        Date d1 = Date.from(Instant.ofEpochMilli(Date.UTC(2001,01,01,0,0,0)));
        assertEquals(Sorters.compareDates(d1,null,false),1);
    }

    //Completion sorter
    @Test
    public void testCompletionSorter() {
        Sorter<Task> gs = Sorters.COMPLETION.get(true);
        Task t1 = new Task(0,"Test task!");
        Task t2 = new Task(1,"Test task!");
        assertEquals(gs.compare(t1,t2),0);
    }

    @Test(expected = NullPointerException.class)
    public void testCompletionSorterWithNullTask() {
        Sorter<Task> gs = Sorters.COMPLETION.get(true);
        Task t1 = null;
        Task t2 = new Task(1,"Test task!");
        assertEquals(gs.compare(t1,t2),0);
    }

    @Test
    public void testCompletionSorterWithCompletedTask() {
        Sorter<Task> gs = Sorters.COMPLETION.get(false);
        Task t1 = new Task(0,"Test task!");
        t1.markComplete(Date.from(Instant.now()));
        Task t2 = new Task(1,"Test task!");
        assertEquals(gs.compare(t1,t2),-1);
    }

    //Priority sorter
    @Test
    public void testPrioritySorter() {
        Sorter<Task> gs = Sorters.PRIORITY.get(true);
        Task t1 = new Task(0,"Test task!");
        Task t2 = new Task(1,"Test task!");
        assertEquals(gs.compare(t1,t2),0);
    }

    @Test(expected = NullPointerException.class)
    public void testPrioritySorterWithNullTask() {
        Sorter<Task> gs = Sorters.PRIORITY.get(true);
        Task t1 = null;
        Task t2 = new Task(1,"Test task!");
        assertEquals(gs.compare(t1,t2),0);
    }

    @Test
    public void testPrioritySorterWithCompletedTask() {
        Sorter<Task> gs = Sorters.PRIORITY.get(false);
        Task t1 = new Task(0,"Test task!");
        t1.markComplete(Date.from(Instant.now()));
        Task t2 = new Task(1,"Test task!");
        assertEquals(gs.compare(t1,t2),-1);
    }

    @Test
    public void testPrioritySorterWithBothCompletedTasks() {
        Sorter<Task> gs = Sorters.PRIORITY.get(false);
        Task t1 = new Task(0,"Test task!");
        t1.markComplete(Date.from(Instant.now()));
        Task t2 = new Task(1,"Test task!");
        t2.markComplete(Date.from(Instant.now()));
        assertEquals(0,gs.compare(t1,t2));
    }
}
