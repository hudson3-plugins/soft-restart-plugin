/*
 * Copyright (c) 2013 Hudson.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Hudson - initial API and implementation and/or initial documentation
 */

package org.hudsonci.lifecycle;

import hudson.lifecycle.Lifecycle;
import hudson.lifecycle.RestartNotSupportedException;
import hudson.model.Hudson;
import java.io.IOException;
import org.eclipse.hudson.init.InitialSetup;
import org.hudsonci.plugins.restart.soft.Messages;

/**
 * Soft restart attempts to restart Hudson within the same JVM.
 * 
 * Requires Hudson 3.1.0
 * 
 * @author Bob Foster
 */
public class SoftRestart extends Lifecycle {

    @Override
    public void restart() throws IOException, InterruptedException {
        if (!canRestart()) {
            throw new IOException(Messages.SoftRestartLifecycle_needSafeRestart());
        }
        InitialSetup.getLastInitialSetup().invokeHudson(true);
    }

    @Override
    public void verifyRestartable() throws RestartNotSupportedException {
        if (!Hudson.getInstance().isSafeRestarting()) {
            throw new RestartNotSupportedException(Messages.SoftRestartLifecycle_needSafeRestart());
        }
    }

    @Override
    public boolean canRestart() {
        return Hudson.getInstance().isSafeRestarting();
    }
    
    /** SoftRestart is <i>only</i> safe restartable. */
    @Override
    public boolean isSafeRestartable() {
        return true;
    }
}
