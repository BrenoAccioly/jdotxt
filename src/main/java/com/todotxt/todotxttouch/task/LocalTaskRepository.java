/**
 * This file is part of Todo.txt Touch, an Android app for managing your todo.txt file (http://todotxt.com).
 *
 * Copyright (c) 2009-2013 Todo.txt contributors (http://todotxt.com)
 *
 * LICENSE:
 *
 * Todo.txt Touch is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any
 * later version.
 *
 * Todo.txt Touch is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with Todo.txt Touch.  If not, see
 * <http://www.gnu.org/licenses/>.
 *
 * @author Todo.txt contributors <todotxt@yahoogroups.com>
 * @license http://www.gnu.org/licenses/gpl.html
 * @copyright 2009-2013 Todo.txt contributors (http://todotxt.com)
 */
package com.todotxt.todotxttouch.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * A repository for tasks working at the local data store level
 * 
 * @author Tim Barlotta
 */
interface LocalTaskRepository {
    void init();

    void purge();

    ArrayList<Task> load();

    void store(ArrayList<Task> tasks);

    void archive(ArrayList<Task> tasks);

    ArrayList<Task> loadDoneTasks();

    void storeDoneTasks(ArrayList<Task> tasks);

    void storeDoneTasks(File file);

    boolean todoFileModifiedSince(Date date);

    boolean doneFileModifiedSince(Date date);

}
