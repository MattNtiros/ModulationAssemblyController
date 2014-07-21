package ModulationAssemblyController.java;

import java.util.Properties;

import org.apache.log4j.Logger;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import CF.InvalidObjectReference;

import org.ossie.component.*;
import org.ossie.properties.*;

import ModulationAssemblyController.java.ports.CF_ResourceOutPort;
/**
 * This is the component code. This file contains all the access points
 * you need to use to be able to access all input and output ports,
 * respond to incoming data, and perform general component housekeeping
 *
 * Source: ModulationAssemblyController.spd.xml
 *
 * @generated
 */
public abstract class ModulationAssemblyController_base extends ThreadedResource {
    /**
     * @generated
     */
    public final static Logger logger = Logger.getLogger(ModulationAssemblyController_base.class.getName());

    /**
     * The property sample_rate
     * If the meaning of this property isn't clear, a description should be added.
     *
     * @generated
     */
    public final FloatProperty sample_rate =
        new FloatProperty(
            "sample_rate", //id
            null, //name
            4000.0F, //default value
            Mode.READWRITE, //mode
            Action.EXTERNAL, //action
            new Kind[] {Kind.CONFIGURE} //kind
            );
    
    /**
     * The property baud_rate
     * If the meaning of this property isn't clear, a description should be added.
     *
     * @generated
     */
    public final FloatProperty baud_rate =
        new FloatProperty(
            "baud_rate", //id
            null, //name
            100.0F, //default value
            Mode.READWRITE, //mode
            Action.EXTERNAL, //action
            new Kind[] {Kind.CONFIGURE} //kind
            );
    
    /**
     * The property tuner_freq
     * If the meaning of this property isn't clear, a description should be added.
     *
     * @generated
     */
    public final FloatProperty tuner_freq =
        new FloatProperty(
            "tuner_freq", //id
            null, //name
            1000.0F, //default value
            Mode.READWRITE, //mode
            Action.EXTERNAL, //action
            new Kind[] {Kind.CONFIGURE} //kind
            );
    
    /**
     * The property mod_level
     * If the meaning of this property isn't clear, a description should be added.
     *
     * @generated
     */
    public final UShortProperty mod_level =
        new UShortProperty(
            "mod_level", //id
            null, //name
            (short)4, //default value
            Mode.READWRITE, //mode
            Action.EXTERNAL, //action
            new Kind[] {Kind.CONFIGURE} //kind
            );
    
    /**
     * The property Advanced
     * If the meaning of this property isn't clear, a description should be added.
     *
     * @generated
     */
    /**
     * The structure for property Advanced
     * 
     * @generated
     */
    public static class Advanced_struct extends StructDef {
        public final UShortProperty oversample_num =
            new UShortProperty(
                "oversample_num", //id
                null, //name
                (short)4, //default value
                Mode.READWRITE, //mode
                Action.EXTERNAL, //action
                new Kind[] {Kind.CONFIGURE} //kind
                );
        public final ULongProperty packet_size =
            new ULongProperty(
                "packet_size", //id
                null, //name
                1000, //default value
                Mode.READWRITE, //mode
                Action.EXTERNAL, //action
                new Kind[] {Kind.CONFIGURE} //kind
                );
    
        /**
         * @generated
         */
        public Advanced_struct(Short oversample_num, Integer packet_size) {
            this();
            this.oversample_num.setValue(oversample_num);
            this.packet_size.setValue(packet_size);
        }
    
        /**
         * @generated
         */
        public Advanced_struct() {
            addElement(this.oversample_num);
            addElement(this.packet_size);
        }
    
        public String getId() {
            return "Advanced";
        }
    };
    
    public final StructProperty<Advanced_struct> Advanced =
        new StructProperty<Advanced_struct>(
            "Advanced", //id
            null, //name
            Advanced_struct.class, //type
            new Advanced_struct(), //default value
            Mode.READWRITE, //mode
            new Kind[] {Kind.CONFIGURE} //kind
            );
    
    // Uses/outputs
    /**
     * @generated
     */
    public CF_ResourceOutPort port_resrc;

    /**
     * @generated
     */
    public ModulationAssemblyController_base()
    {
        super();

        // Properties
        addProperty(sample_rate);
        addProperty(baud_rate);
        addProperty(tuner_freq);
        addProperty(mod_level);
        addProperty(Advanced);

        // Uses/outputs
        this.port_resrc = new CF_ResourceOutPort("resrc");
        this.addPort("resrc", this.port_resrc);
    }

    public void start() throws CF.ResourcePackage.StartError
    {
        super.start();
    }

    public void stop() throws CF.ResourcePackage.StopError
    {
        super.stop();
    }

    /**
     * The main function of your component.  If no args are provided, then the
     * CORBA object is not bound to an SCA Domain or NamingService and can
     * be run as a standard Java application.
     * 
     * @param args
     * @generated
     */
    public static void main(String[] args) 
    {
        final Properties orbProps = new Properties();
        ModulationAssemblyController.configureOrb(orbProps);

        try {
            ThreadedResource.start_component(ModulationAssemblyController.class, args, orbProps);
        } catch (InvalidObjectReference e) {
            e.printStackTrace();
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (InvalidName e) {
            e.printStackTrace();
        } catch (ServantNotActive e) {
            e.printStackTrace();
        } catch (WrongPolicy e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
