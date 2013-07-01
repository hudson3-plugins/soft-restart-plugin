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

package org.hudsonci.plugins.restart.soft;

import hudson.lifecycle.Lifecycle;
import hudson.lifecycle.RestartNotSupportedException;
import hudson.model.Hudson;
import java.io.IOException;
import org.eclipse.hudson.init.InitialSetup;

/**
 *
 * @author Bob Foster
 */
public class SoftRestartLifecycle extends Lifecycle {

    @Override
    public void restart() throws IOException, InterruptedException {
        if (!canRestart()) {
            throw new IOException(Messages.SoftRestartLifecycle_needSafeRestart());
        }
        InitialSetup.getLastInitialSetup().invokeHudson();
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

}
