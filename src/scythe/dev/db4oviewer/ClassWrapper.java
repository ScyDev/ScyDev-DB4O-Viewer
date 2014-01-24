package scythe.dev.db4oviewer;

import com.db4o.reflect.ReflectClass;

public class ClassWrapper {
	
	ReflectClass theClass;

	public ClassWrapper(ReflectClass theClass)
	{ 
		this.theClass = theClass;
	}
	
	public String toString()
	{
		return this.theClass.getName();
	}

	public ReflectClass getTheClass() {
		return theClass;
	}
}
