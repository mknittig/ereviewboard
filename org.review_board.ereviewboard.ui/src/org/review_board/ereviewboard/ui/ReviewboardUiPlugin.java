/*******************************************************************************
 * Copyright (c) 2004 - 2009 Mylyn project committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mylyn project committers, Atlassian, Sven Krzyzak
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2009 Markus Knittig
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * Contributors:
 *     Markus Knittig - adapted Trac, Redmine & Atlassian implementations for
 *                      Review Board
 *******************************************************************************/
package org.review_board.ereviewboard.ui;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.mylyn.tasks.ui.TaskRepositoryLocationUiFactory;
import org.eclipse.mylyn.tasks.ui.TasksUi;
import org.osgi.framework.BundleContext;
import org.review_board.ereviewboard.core.ReviewboardCorePlugin;

/**
 * @author Markus Knittig
 *
 */
public class ReviewboardUiPlugin extends Plugin {

    public static final String PLUGIN_ID = "org.review_board.ereviewboard.ui";

    private static volatile ReviewboardUiPlugin DEFAULT;

    public void start(BundleContext context) throws Exception {
        super.start(context);
        ReviewboardCorePlugin corePlugin = ReviewboardCorePlugin.getDefault();

        assert corePlugin != null;
        corePlugin.getConnector().setTaskRepositoryLocationFactory(
                new TaskRepositoryLocationUiFactory());
        TasksUi.getRepositoryManager().addListener(corePlugin.getConnector().getClientManager());
        
        DEFAULT = this;
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {

        DEFAULT = null;
    }
    
    public static ReviewboardUiPlugin getDefault() {
        
        return DEFAULT;
    }

}
