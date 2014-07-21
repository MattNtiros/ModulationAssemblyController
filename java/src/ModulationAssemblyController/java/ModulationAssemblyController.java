package ModulationAssemblyController.java;

import java.awt.RenderingHints.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.omg.CORBA.Any;
import org.omg.CORBA.AnySeqHelper;
import org.omg.CORBA.AnySeqHolder;
import org.omg.CORBA.Object;
import org.ossie.properties.FloatProperty;
import org.ossie.properties.PropertyListener;
import org.ossie.properties.StructDef;
import org.ossie.properties.UShortProperty;

import com.sun.corba.se.impl.orbutil.GetPropertyAction;

import CF.DataType;
import CF.PropertiesHelper;
import CF.PropertiesHolder;
import CF.Resource;
import CF.ResourceOperations;
import CF.UnknownProperties;
import CF.PortSupplierPackage.UnknownPort;
import CF.PropertySetPackage.InvalidConfiguration;
import CF.PropertySetPackage.PartialConfiguration;
import ModulationAssemblyController.java.ports.CF_ResourceOutPort;

/**
 * This is the component code. This file contains the derived class where custom
 * functionality can be added to the component. You may add methods and code to
 * this class to handle property changes, respond to incoming data, and perform
 * general component housekeeping
 *
 * Source: ModulationAssemblyController.spd.xml
 */
public class ModulationAssemblyController extends ModulationAssemblyController_base {
	/**
	 * This is the component constructor. In this method, you may add additional
	 * functionality to properties, such as listening for changes and handling
	 * allocation, and set up internal state for your component.
	 *
	 * A component may listen for external changes to properties (i.e., by a
	 * call to configure) using the PropertyListener interface. Listeners are
	 * registered by calling addChangeListener() on the property instance
	 * with an object that implements the PropertyListener interface for that
	 * data type (e.g., "PropertyListener<Float>" for a float property). More
	 * than one listener can be connected to a property.
	 *
	 *   Example:
	 *       // This example makes use of the following properties:
	 *       //  - A float value called scaleValue
	 *       // The file must import "org.ossie.properties.PropertyListener"
	 *       // Add the following import to the top of the file:
	 *       import org.ossie.properties.PropertyListener;
	 *
	 *       //Add the following to the class constructor:
	 *       this.scaleValue.addChangeListener(new PropertyListener<Float>() {
	 *           public void valueChanged(Float oldValue, Float newValue) {
	 *               scaleValueChanged(oldValue, newValue);
	 *           }
	 *       });
	 *
	 *       //Add the following method to the class:
	 *       private void scaleValueChanged(Float oldValue, Float newValue)
	 *       {
	 *          logger.debug("Changed scaleValue " + oldValue + " to " + newValue);
	 *       }
	 *
	 * The recommended practice is for the implementation of valueChanged() to
	 * contain only glue code to dispatch the call to a private method on the
	 * component class.
	 */
	
	
	public ModulationAssemblyController() {
		super();
		this.sample_rate.addChangeListener(new PropertyListener<Float>() {
			
			@Override
			public void valueChanged(Float oldvalue, Float newValue) {
				// 
				changeFloat(oldvalue, newValue, "sample_rate");
			}
		});
		this.tuner_freq.addChangeListener(new PropertyListener<Float>() {
			
			@Override
			public void valueChanged(Float oldvalue, Float newValue) {
				// 
				changeFloat(oldvalue, newValue, "frequency_shift");
			}
		});
		this.baud_rate.addChangeListener(new PropertyListener<Float>() {

			@Override
			public void valueChanged(Float oldvalue, Float newValue) {
				// 
				changeFloat(oldvalue, newValue, "baud_rate");
			}
		});
		this.mod_level.addChangeListener(new PropertyListener<Short>() {
			
			@Override
			public void valueChanged(Short oldvalue, Short newValue) {
				// 
				changeUShort(oldvalue.shortValue(), newValue.shortValue(), "levels");
			}
		});
		this.Advanced.addChangeListener(new PropertyListener<Advanced_struct>() {

			@Override
			public void valueChanged(Advanced_struct oldvalue, Advanced_struct newValue) {
				// 
				changeUShort(oldvalue.oversample_num.getValue(), newValue.oversample_num.getValue(), "oversample_num");
				changeULong(oldvalue.packet_size.getValue(), newValue.packet_size.getValue(), "packet_size");
			}
		});
	}

	/**
	 *
	 * Main processing function
	 *
	 * General functionality:
	 *
	 * The serviceFunction() is called repeatedly by the component's processing
	 * thread, which runs independently of the main thread. Each invocation
	 * should perform a single unit of work, such as reading and processing one
	 * data packet.
	 *
	 * The return status of serviceFunction() determines how soon the next
	 * invocation occurs:
	 *   - NORMAL: the next call happens immediately
	 *   - NOOP:   the next call happens after a pre-defined delay (100 ms)
	 *   - FINISH: no more calls occur
	 *
	 * StreamSRI:
	 *    To create a StreamSRI object, use the following code:
	 *            String stream_id = "testStream";
	 *            BULKIO.StreamSRI sri = new BULKIO.StreamSRI();
	 *            sri.mode = 0;
	 *            sri.xdelta = 0.0;
	 *            sri.ydelta = 1.0;
	 *            sri.subsize = 0;
	 *            sri.xunits = 1; // TIME_S
	 *            sri.streamID = (stream_id != null) ? stream_id : "";
	 *
	 * PrecisionUTCTime:
	 *    To create a PrecisionUTCTime object, use the following code:
	 *            BULKIO.PrecisionUTCTime tstamp = bulkio.time.utils.now();
	 *
	 * Ports:
	 *
	 *    Each port instance is accessed through members of the following form:
	 *
	 *        this.port_<PORT NAME>
	 *
	 *    Input BULKIO data is obtained by calling getPacket on the provides
	 *    port. The getPacket method takes one argument: the time to wait for
	 *    data to arrive, in milliseconds. A timeout of 0 causes getPacket to
	 *    return immediately, while a negative timeout indicates an indefinite
	 *    wait. If no data is queued and no packet arrives during the waiting
	 *    period, getPacket returns null.
	 *
	 *    Output BULKIO data is sent by calling pushPacket on the uses port. In
	 *    the case of numeric data, the pushPacket method takes a primitive
	 *    array (e.g., "float[]"), a timestamp, an end-of-stream flag and a
	 *    stream ID. You must make at least one call to pushSRI to associate a
	 *    StreamSRI with the stream ID before calling pushPacket, or receivers
	 *    may drop the data.
	 *
	 *    When all processing on a stream is complete, a call should be made to
	 *    pushPacket with the end-of-stream flag set to "true".
	 *
	 *    Interactions with non-BULKIO ports are left up to the discretion of
	 *    the component developer.
	 *
	 * Properties:
	 *
	 *    Properties are accessed through members of the same name; characters
	 *    that are invalid for a Java identifier are replaced with "_". The
	 *    current value of the property is read with getValue and written with
	 *    setValue:
	 *
	 *        float val = this.float_prop.getValue();
	 *        ...
	 *        this.float_prop.setValue(1.5f);
	 *
	 *    Primitive data types are stored using the corresponding Java object
	 *    wrapper class. For example, a property of type "float" is stored as a
	 *    Float. Java will automatically box and unbox primitive types where
	 *    appropriate.
	 *
	 *    Numeric properties support assignment via setValue from any numeric
	 *    type. The standard Java type coercion rules apply (e.g., truncation
	 *    of floating point values when converting to integer types).
	 *
	 * Example:
	 *
	 *    This example assumes that the component has two ports:
	 *        - A bulkio.InShortPort provides (input) port called dataShort_in
	 *        - A bulkio.OutFloatPort uses (output) port called dataFloat_out
	 *    The mapping between the port and the class is found in the component
	 *    base class file.
	 *    This example also makes use of the following Properties:
	 *        - A float value called amplitude with a default value of 2.0
	 *        - A boolean called increaseAmplitude with a default value of true
	 *
	 *    bulkio.InShortPort.Packet data = this.port_dataShort_in.getPacket(125);
	 *
	 *    if (data != null) {
	 *        float[] outData = new float[data.getData().length];
	 *        for (int i = 0; i < data.getData().length; i++) {
	 *            if (this.increaseAmplitude.getValue()) {
	 *                outData[i] = (float)data.getData()[i] * this.amplitude.getValue();
	 *            } else {
	 *                outData[i] = (float)data.getData()[i];
	 *            }
	 *        }
	 *
	 *        // NOTE: You must make at least one valid pushSRI call
	 *        if (data.sriChanged()) {
	 *            this.port_dataFloat_out.pushSRI(data.getSRI());
	 *        }
	 *        this.port_dataFloat_out.pushPacket(outData, data.getTime(), data.getEndOfStream(), data.getStreamID());
	 *    }
	 *
	 */

	protected int serviceFunction() {
		logger.debug("serviceFunction() example log message");
		
		//Initial config
		changeUShort((short)0, this.mod_level.getValue(), "levels");
		changeFloat((float)0, this.tuner_freq.getValue(), "frequency_shift");
		
		changeUShort((short)0, this.Advanced.getValue().oversample_num.getValue(), "oversample_num");
		changeFloat((float)0, this.baud_rate.getValue(), "baud_rate");
		changeFloat((float)0, this.sample_rate.getValue(), "sample_rate");
		changeULong(0, this.Advanced.getValue().packet_size.getValue(), "packet_size");
		
		return FINISH;
	}


	/**
	 * Set additional options for ORB startup. For example:
	 *
	 *   orbProps.put("com.sun.CORBA.giop.ORBFragmentSize", Integer.toString(fragSize));
	 *
	 * @param orbProps
	 */
	public static void configureOrb(final Properties orbProps) {
	}




	/*
	 * ChangeListener Functions
	 */
	public void changeFloat(Float old, Float updated, String id)	{
		if(id.equals("frequency_shift"))	{
			//change frequency tuner
			DataType prop = getProperty(id);
			prop.value.insert_float(this.tuner_freq.getValue());
			if(this.tuner_freq.getValue() > (this.sample_rate.getValue()/2))
				System.out.println("WARNING - Tuner frequency set to above Fs/2. Aliasing can occur");
			setProperty(prop);
		} else if(id.equals("baud_rate")) {
				update_baudRate();
		} else if(id.equals("sample_rate"))	{
			update_sampleRate();
		}
	}



	public void changeUShort(Short old, Short updated, String id)	{
		DataType prop = getProperty(id);
		if(id.equals("levels"))	{	//if changing mod_level
			prop.value.insert_ushort(this.mod_level.getValue());
			setProperty(prop);
		}	else if(id.equals("oversample_num"))	{	//if changing oversample_num
			prop.value.insert_ushort(this.Advanced.getValue().oversample_num.getValue());
			setProperty(prop);
			//change baud_rate
			update_baudRate();
		}
	}
	public void changeULong(Integer old, Integer updated, String id)	{
		if(id.equals("packet_size"))	{
			//changes packet size for PSKBasebandMod
			
			//gets values needed
			int this_packet_size = this.Advanced.getValue().packet_size.getValue();
			short upsample_factor = getProperty("upsample_factor").value.extract_ushort();
			//calculates new value, restricts to bounds also
			int new_packet_size = this_packet_size/upsample_factor;
			if(new_packet_size <=0)
				new_packet_size = 1;
			
			//puts new packet size into the packet_size property value in PSKMod
			DataType prop = getProperty(id);
			prop.value.insert_ulong(new_packet_size);
			setProperty(prop);
			
			//updates current packet_size to reflect what new_packet_size was chosen
			int value = new_packet_size*upsample_factor;
			this.Advanced.getValue().packet_size.setValue(value);
		}
	}


	
	
	/*
	 * Private Functions
	 */
	/*
	 * 
	 * Functions to take care of each property change
	 * 
	 */
	
	private void update_baudRate() {
		//gets values needed
		short oversample_num = getProperty("oversample_num").value.extract_ushort();
		float bRate = this.baud_rate.getValue();
		
		//calculates what xdelta needs to be for baud_rate
		double new_xdelta = 1/(bRate * (float)oversample_num);
		
		//sets the xdelta in PSKMod
		DataType xdel_prop = getProperty("xdelta");
		xdel_prop.value.insert_double(new_xdelta);
		setProperty(xdel_prop);
		
		//update sample_rate
		update_sampleRate();
	}
	
	
	private void update_sampleRate() {
		//gets values needed
		double xdelta = getProperty("xdelta").value.extract_double();
		float output_fs = this.sample_rate.getValue();
		
		//calculated upsample factor from values
		short new_upsample_factor = (short) (output_fs*xdelta);
		if(new_upsample_factor <= 0) //restricts bounds
			new_upsample_factor = 1;
		
		//change upsample_factor
		DataType upsample_prop = getProperty("upsample_factor");
		upsample_prop.value.insert_ushort(new_upsample_factor);
		setProperty(upsample_prop);
		
		//updates the output sample rate depending on new_upsample_factor
		float updated_fs = (float)(new_upsample_factor/xdelta);
		this.sample_rate.setValue(updated_fs);
		
		//change filter properties to filter off copies depending on sample_rate
		//calculates filter properties
		double lowpass_freq = 1/(xdelta*2); // Fs/2
		double transition_width = lowpass_freq*.025;
		
		//gets properties, changes them, and sets them
		DataType prop_freq1 = getProperty("freq1");
		DataType prop_transition_width = getProperty("TransitionWidth");
		prop_freq1.value.insert_double(lowpass_freq);
		prop_transition_width.value.insert_double(transition_width);
		setProperty(prop_freq1);
		setProperty(prop_transition_width);
		
		//update packetsize depending on upsample_factor (<--since this changed)
		changeULong(this.Advanced.getValue().packet_size.getValue(),
					this.Advanced.getValue().packet_size.getValue(), "packet_size");
	}
	

	
	
	
	//NOT CONFIGURED TO GET SEQUENCES.. only simples and simples from within structs
	
	/*
	 * Function to get a property from a component using the property name. 
	 * Will try to get a property as close to the property name as possible.
	 * If no property is found, will return null.
	 * 
	 * After getting property, change the value as needed and use setProperty
	 * to configure it to the component
	 */
	private DataType getProperty(String propertyName)
	{
		HashMap<String, ResourceOperations> allPorts = this.port_resrc.getPorts();	
		for(Entry<String, ResourceOperations> entry : allPorts.entrySet())	{
			ResourceOperations comp = entry.getValue();
			try	{
				DataType[] properties = new DataType[0];
				PropertiesHolder holder = new PropertiesHolder(properties);
				comp.query(holder);
				properties = holder.value;
				for(int i=0;i<properties.length;i++)	{
					//if the property is a struct identified by the type "properties"
					if(properties[i].value.type().toString().toLowerCase().contains("properties"))	{
						DataType[] struc_properties = PropertiesHelper.extract(properties[i].value);
						for(int i1=0;i1<struc_properties.length;i1++)
							if(struc_properties[i1].id.contains(propertyName))
								return struc_properties[i1];
					} else if(properties[i].id.contains(propertyName))	{
						//if it's not a struct
						return properties[i];
					}
				}
			} catch(Exception e)
			{
				//if any error, print it out and stop system.
				System.out.println("Error: " + e.getMessage());
				System.exit(0);
			}
		}
		property_not_found();	//if it comes to this part of the program
								//should exit because property was not found.
		return null;//<---should never be called
	}
	
	/*
	 *	Function to push out a property into a component. Use this 
	 *  in conjuction with the getProperty function. 
	 */
	private void setProperty(DataType Prop)	{
		HashMap<String, ResourceOperations> allPorts = this.port_resrc.getPorts();
		boolean found = false;
		for(Entry<String, ResourceOperations> entry : allPorts.entrySet())	{
			if(found)
				break;
			ResourceOperations comp = entry.getValue();
			try	{
				//gets new property ready to configure
				DataType[] newProperty = new DataType[1];
				newProperty[0] = new DataType(Prop.id, getOrb().create_any());
				newProperty[0].value = Prop.value;
				
				//gets component properties.
				DataType[] properties = new DataType[0];
				PropertiesHolder holder = new PropertiesHolder(properties);
				comp.query(holder);
				properties = holder.value;
				//find which component had the property and push out the new property
				for(int i=0;i<properties.length;i++)	{
					boolean isStruct = false;
					if(found)
						break;
					if(properties[i].value.type().toString().toLowerCase().contains("properties"))	{
						//if its a struct
						DataType[] struc_properties = PropertiesHelper.extract(properties[i].value);
						for(int k=0;k<struc_properties.length;k++)	{
							if(struc_properties[k].id.equals(Prop.id))	{
								//changing struct property value
								struc_properties[k].value = Prop.value;
								isStruct = true;
								found = true;
							}
						}
						if(isStruct && found)	{
							//inserting struct properties into properties[i].value
							//after changing specific struct property value above.
							PropertiesHelper.insert(properties[i].value, struc_properties);
							//if it's struct, new property should contain all properties inside struct to configure
							newProperty[0].id = properties[i].id;
							newProperty[0].value = properties[i].value;
							//pushes the new property out 
							comp.configure(newProperty);
						}
					} else if(properties[i].id.equals(Prop.id))	{
						//if it's not a struct, push new property out
						comp.configure(newProperty);
						found = true;
					}	
				}
			} catch(Exception e)
			{
				System.out.println("Error: " + e.getMessage());
				System.exit(0);
			}
		}
	}

	//property not found function
	private void property_not_found()	{
		System.err.println("Property was not found");
		System.err.println("Please make sure all components are connected!");
		System.exit(0);
	}
}



/* --------------------------------------------------------------------------
 * Example of getting property and setting property for a component
 * --------------------------------------------------------------------------
if(comp.identifier().contains("FreqShift"))
{
	try {
		System.out.println("FreqShift");
		System.out.println("------------------------");
		DataType[] fs_properties = new DataType[0];
		DataType[] setPro = new DataType[1];
		setPro[0] = new DataType("frequency_shift", getOrb().create_any());
		PropertiesHolder properties = new PropertiesHolder(fs_properties);
		//query the property
		comp.query(properties);
		for (DataType prop : properties.value) {
			System.out.println(prop.id);
			if(prop.id.contains("shift"))
				setPro[0].id = prop.id;
			System.out.println(prop.value.extract_float());
		}
		//sets the property 
		setPro[0].value.insert_float(this.sample_rate.getValue());
		comp.configure(setPro);
	} catch (Exception e)	{
		System.out.println("exception");
		System.out.println(e.getMessage());
	}
}
*/


/* --------------------------------------------------------------------------
 * Example of getting and setting property in a struct
 * --------------------------------------------------------------------------
if(comp.identifier().contains("fastfilter"))
{
	try {
		
		//to push in filterProps whole
		DataType[] newFilterProps = new DataType[1];
		newFilterProps[0] = new DataType("", getOrb().create_any());
		
		
		DataType[] properties = new DataType[0];
		PropertiesHolder holder = new PropertiesHolder(properties);
		comp.query(holder);
		properties = holder.value;
		for(int i=0;i<properties.length; i++)
		{
			if(properties[i].id.contains("filterProps"))
			{
				//set id for newFilterProps from filterProps
				newFilterProps[0].id = properties[i].id;
				
				//show values of filterProps
				System.out.println(properties[i].id);
				showStructProperties(properties[i].value);
				
				//change value in filterProps
				DataType[] filterProps = PropertiesHelper.extract(properties[i].value);
				for(int i1=0;i1<filterProps.length;i1++)
				{
					if(filterProps[i1].id.contains("freq1"))
					{
						filterProps[i1].value.insert_double(this.sample_rate.getValue());
					} else if(filterProps[i1].id.contains("freq2"))
					{
						filterProps[i1].value.insert_double(this.sample_rate.getValue()*2);
					}
				}
				
				//inserts filterProps into properties[i].value
				PropertiesHelper.insert(properties[i].value, filterProps);
				
				//set the newfilterProps value 
				newFilterProps[0].value = properties[i].value;
			}
		}
		//push out the configured filterProps
		comp.configure(newFilterProps);
	} catch (Exception e) {
		System.out.println("error: " + e.getMessage());
	}
}
 */








/*
 * --------------------------------------------------------------------------
 * Functions below to display component properties.
 * Goes through all of them and if there is a struct, it will display
 * the struct properties as well.
 * --------------------------------------------------------------------------
 */
/*
public static void showCompProperties(ResourceOperations comp)	{

	try	{
		System.out.println("Component: " + comp.identifier());
		DataType[] comp_props = new DataType[0];
		PropertiesHolder props = new PropertiesHolder(comp_props);
		comp.query(props);
		for (DataType thisProp : props.value) {
			System.out.print(thisProp.id + ":\t");
			showAny(thisProp.value);
		}

	} catch(Exception e)
	{
		System.out.println("ERR-showCompProperties: " + e.getMessage());
	}
	System.out.println("");
}

public static void showAny(Any thisAny)	{
	try	{
		if(thisAny.type().toString().toLowerCase().contains("float"))
			System.out.println(thisAny.extract_float());
		else if(thisAny.type().toString().toLowerCase().contains("double"))
			System.out.println(thisAny.extract_double());
		else if(thisAny.type().toString().toLowerCase().contains("ulong"))
			System.out.println(thisAny.extract_ulong());
		else if(thisAny.type().toString().toLowerCase().contains("long"))
			System.out.println(thisAny.extract_long());
		else if(thisAny.type().toString().toLowerCase().contains("string"))
			System.out.println(thisAny.extract_string());
		else if(thisAny.type().toString().toLowerCase().contains("ushort"))
			System.out.println(thisAny.extract_ushort());
		else if(thisAny.type().toString().toLowerCase().contains("short"))
			System.out.println(thisAny.extract_short());
		else if(thisAny.type().toString().toLowerCase().contains("boolean"))
			System.out.println(thisAny.extract_boolean());
		else if(thisAny.type().toString().toLowerCase().contains("properties"))
		{
			System.out.println("");
			showStructProperties(thisAny);
		}
		else
		{
			int te = thisAny.type().toString().indexOf("\n");
			System.out.println(thisAny.type().toString().substring(te+1));
		}
	} catch (Exception e)	{
		System.out.println("E- ns");
	}
}

public static void showStructProperties(Any struct_any)	{
	DataType[] properties = PropertiesHelper.extract(struct_any);
	for(int i=0;i<properties.length;i++)
	{
		System.out.print("\t" + properties[i].id + ": ");
		showAny(properties[i].value);
	}
}



*/
