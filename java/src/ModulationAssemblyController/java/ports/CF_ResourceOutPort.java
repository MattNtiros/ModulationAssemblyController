package ModulationAssemblyController.java.ports;

import java.util.HashMap;
import java.util.Map;
import org.ossie.component.QueryableUsesPort;

/**
 * @generated
 */
public class CF_ResourceOutPort extends QueryableUsesPort<CF.ResourceOperations> implements CF.ResourceOperations {

    /**
     * Map of connection Ids to port objects
     * @generated
     */
    protected Map<String, CF.ResourceOperations> outConnections = new HashMap<String, CF.ResourceOperations>();

    /**
     * @generated
     */
    public CF_ResourceOutPort(String portName) 
    {
        super(portName);

        this.outConnections = new HashMap<String, CF.ResourceOperations>();
        //begin-user-code
        //end-user-code
    }

    /**
     * @generated
     */
    protected CF.ResourceOperations narrow(org.omg.CORBA.Object connection) 
    {
        CF.ResourceOperations ops = CF.ResourceHelper.narrow(connection);
        
        //begin-user-code 
        //end-user-code 
        
        return ops; 
    }
    public void connectPort(final org.omg.CORBA.Object connection, final String connectionId) throws CF.PortPackage.InvalidPort, CF.PortPackage.OccupiedPort
    {
        try {
            // don't want to process while command information is coming in
            synchronized (this.updatingPortsLock) {
                super.connectPort(connection, connectionId);
                final CF.ResourceOperations port = CF.ResourceHelper.narrow(connection);
                this.outConnections.put(connectionId, port);
                this.active = true;
            }
        } catch (final Throwable t) {
            t.printStackTrace();
        }

    }

    public void disconnectPort(final String connectionId){
        // don't want to process while command information is coming in
        synchronized (this.updatingPortsLock) {
            super.disconnectPort(connectionId);
            this.outConnections.remove(connectionId);
            this.active = (this.outConnections.size() != 0);
        }
    }

   /**
     * @generated
     */
    public void initialize() throws CF.LifeCyclePackage.InitializeError
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.initialize();
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public void releaseObject() throws CF.LifeCyclePackage.ReleaseError
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.releaseObject();
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public void runTest(int testid, CF.PropertiesHolder testValues) throws CF.TestableObjectPackage.UnknownTest, CF.UnknownProperties
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.runTest(testid, testValues);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public void configure(CF.DataType[] configProperties) throws CF.PropertySetPackage.InvalidConfiguration, CF.PropertySetPackage.PartialConfiguration
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.configure(configProperties);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public void query(CF.PropertiesHolder configProperties) throws CF.UnknownProperties
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.query(configProperties);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public org.omg.CORBA.Object getPort(String name) throws CF.PortSupplierPackage.UnknownPort
    {
        org.omg.CORBA.Object retval = null;

        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    retval = p.getPort(name);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return retval;
    }
 
   /**
     * @generated
     */
    public CF.LogEvent[] retrieve_records(org.omg.CORBA.IntHolder howMany, int startingRecord)
    {
        CF.LogEvent[] retval = null;

        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    retval = p.retrieve_records(howMany, startingRecord);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return retval;
    }
 
   /**
     * @generated
     */
    public CF.LogEvent[] retrieve_records_by_date(org.omg.CORBA.IntHolder howMany, long to_timeStamp)
    {
        CF.LogEvent[] retval = null;

        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    retval = p.retrieve_records_by_date(howMany, to_timeStamp);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return retval;
    }
 
   /**
     * @generated
     */
    public CF.LogEvent[] retrieve_records_from_date(org.omg.CORBA.IntHolder howMany, long from_timeStamp)
    {
        CF.LogEvent[] retval = null;

        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    retval = p.retrieve_records_from_date(howMany, from_timeStamp);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return retval;
    }
 
   /**
     * @generated
     */
    public void setLogLevel(String logger_id, int newLevel) throws CF.UnknownIdentifier
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.setLogLevel(logger_id, newLevel);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public String getLogConfig()
    {
        String retval = "";

        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    retval = p.getLogConfig();
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return retval;
    }
 
   /**
     * @generated
     */
    public void setLogConfig(String config_contents)
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.setLogConfig(config_contents);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public void setLogConfigURL(String config_url)
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.setLogConfigURL(config_url);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public void start() throws CF.ResourcePackage.StartError
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.start();
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public void stop() throws CF.ResourcePackage.StopError
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.stop();
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public int log_level()
    {
        int retval = 0;

        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    retval = p.log_level();
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return retval;
    }
 
   /**
     * @generated
     */
    public void log_level(int data)
    {
        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    p.log_level(data);
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return;
    }
 
   /**
     * @generated
     */
    public String identifier()
    {
        String retval = "";

        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    retval = p.identifier();
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return retval;
    }
 
   /**
     * @generated
     */
    public boolean started()
    {
        boolean retval = false;

        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    retval = p.started();
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return retval;
    }
 
   /**
     * @generated
     */
    public String softwareProfile()
    {
        String retval = "";

        synchronized(this.updatingPortsLock) {    // don't want to process while command information is coming in
            if (this.active) {
                //begin-user-code
                //end-user-code
                
                for (CF.ResourceOperations p : this.outConnections.values()) {
                    retval = p.softwareProfile();
                }
            }
        }    // don't want to process while command information is coming in
        
        //begin-user-code
        //end-user-code
        
        return retval;
    }
 }
