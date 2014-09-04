package org.opendaylight.defense4all.core.impl;

import org.opendaylight.defense4all.core.AMSRep;
import org.opendaylight.defense4all.core.DFAppModule;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain;

/**
 * Created by sagii on 8/26/14.
 */
public class DefaultAmsRep extends DFAppModule implements AMSRep{


    @Override
    public void init() throws ExceptionControlApp {

    }

    @Override
    public void finit() {

    }

    @Override
    public void reset(FrameworkMain.ResetLevel resetLevel) throws ExceptionControlApp {

    }

    @Override
    public void addAMS(String amsKey) throws ExceptionControlApp {

    }

    @Override
    public void removeAMS(String amsKey) throws ExceptionControlApp {

    }

    @Override
    public void addSecurityConfiguration(String dvsnInfoKey) throws ExceptionControlApp {

    }

    @Override
    public void removeSecurityConfiguration(String dvsnInfoKey) throws ExceptionControlApp {

    }

    @Override
    public void startMonitoring(String mitigationKey) throws ExceptionControlApp {

    }

    @Override
    public void stopMonitoring(String mitigationKey) throws ExceptionControlApp {

    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public void addPN(String pnKey) throws ExceptionControlApp {

    }

    @Override
    public void removePN(String pnKey) throws ExceptionControlApp {

    }


    @Override
    protected void actionSwitcher(int actionCode, Object param) {

    }
}
