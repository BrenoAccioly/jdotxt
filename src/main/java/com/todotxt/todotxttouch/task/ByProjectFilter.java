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

import java.util.ArrayList;
import java.util.List;

/**
 * A filter that matches Tasks containing the specified projects
 * 
 * @author Tim Barlotta
 */
class ByProjectFilter implements Filter<Task> {
    private ArrayList<String> projects = new ArrayList<String>();

    public ByProjectFilter(List<String> projects) {
        if (projects != null) {
            this.projects.addAll(projects);
        }
    }

    @Override
    public boolean apply(Task input) {
        if (projects.size() == 0) {
            return true;
        }
        for (String p : input.getProjects()) {
            if (projects.contains(p)) {
                return true;
            }
        }
        /*
         * Match tasks without project if filter contains "-"
         */
        if (input.getProjects().size()==0 && projects.contains("-")) {
            return true;
        }
        return false;
    }

    /* FOR TESTING ONLY, DO NOT USE IN APPLICATION */
    ArrayList<String> getProjects() {
        return projects;
    }
}
