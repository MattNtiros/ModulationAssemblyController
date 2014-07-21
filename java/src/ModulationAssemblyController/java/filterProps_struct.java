package ModulationAssemblyController.java;

import org.ossie.properties.Action;
import org.ossie.properties.BooleanProperty;
import org.ossie.properties.DoubleProperty;
import org.ossie.properties.Kind;
import org.ossie.properties.Mode;
import org.ossie.properties.StringProperty;
import org.ossie.properties.StructDef;

class filterProps_struct extends StructDef {
    public final DoubleProperty TransitionWidth =
        new DoubleProperty(
            "TransitionWidth", //id
            null, //name
            800.0, //default value
            Mode.READWRITE, //mode
            Action.EXTERNAL, //action
            new Kind[] {Kind.CONFIGURE} //kind
            );
    public final StringProperty Type =
        new StringProperty(
            "Type", //id
            null, //name
            "lowpass", //default value
            Mode.READWRITE, //mode
            Action.EXTERNAL, //action
            new Kind[] {Kind.CONFIGURE} //kind
            );
    public final DoubleProperty Ripple =
        new DoubleProperty(
            "Ripple", //id
            null, //name
            0.01, //default value
            Mode.READWRITE, //mode
            Action.EXTERNAL, //action
            new Kind[] {Kind.CONFIGURE} //kind
            );
    public final DoubleProperty freq1 =
        new DoubleProperty(
            "freq1", //id
            null, //name
            1000.0, //default value
            Mode.READWRITE, //mode
            Action.EXTERNAL, //action
            new Kind[] {Kind.CONFIGURE} //kind
            );
    public final DoubleProperty freq2 =
        new DoubleProperty(
            "freq2", //id
            null, //name
            2000.0, //default value
            Mode.READWRITE, //mode
            Action.EXTERNAL, //action
            new Kind[] {Kind.CONFIGURE} //kind
            );
    public final BooleanProperty filterComplex =
        new BooleanProperty(
            "filterComplex", //id
            null, //name
            false, //default value
            Mode.READWRITE, //mode
            Action.EXTERNAL, //action
            new Kind[] {Kind.CONFIGURE} //kind
            );

    /**
     * @generated
     */
    public filterProps_struct(Double TransitionWidth, String Type, Double Ripple, Double freq1, Double freq2, Boolean filterComplex) {
        this();
        this.TransitionWidth.setValue(TransitionWidth);
        this.Type.setValue(Type);
        this.Ripple.setValue(Ripple);
        this.freq1.setValue(freq1);
        this.freq2.setValue(freq2);
        this.filterComplex.setValue(filterComplex);
    }

    /**
     * @generated
     */
    public filterProps_struct() {
        addElement(this.TransitionWidth);
        addElement(this.Type);
        addElement(this.Ripple);
        addElement(this.freq1);
        addElement(this.freq2);
        addElement(this.filterComplex);
    }

    public String getId() {
        return "filterProps";
    }
};