package enums;

public enum VehicleCategory {
	HATCH,
	SEDA,
	PERUA,
	COUPE,
	CONVERSIVEL,
	MINIVAN,
	SUV,
	LIMOUSINE;
	
	public static VehicleCategory fromOrdinal(int index) {
		for(VehicleCategory category : VehicleCategory.values()) {
			if(category.ordinal() == index) {
				return category;
			}
		}
		
		throw new IllegalArgumentException("Valor de categoria inexistente");
	}
}
