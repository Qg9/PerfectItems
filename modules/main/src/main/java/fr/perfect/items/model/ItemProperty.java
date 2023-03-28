package fr.perfect.items.model;

import fr.perfect.items.data.FarmHoeData;
import fr.perfect.items.data.FarmSwordData;
import fr.perfect.items.data.ItemData;
import fr.perfect.items.data.simple.*;

public enum ItemProperty {

	FARMHOE(FarmHoeData.class),
	HAMMER(IntegerData.class),
	KEEPINVENTORY(EmptyData.class),
	FARMSWORD(FarmSwordData.class),
	SELLSTICK(DoubleData.class),
	LINEBUILDER(MaterialData.class),
	CHESTVIEWER(EmptyData.class),
	CROWBAR(EmptyData.class),
	INVSEE(EmptyData.class),

	DURABILITY(IntegerData.class),
	OWNERNAME(StringData.class)
	;

	private final Class<? extends ItemData> tclass;

	ItemProperty(Class<? extends ItemData> tclass) {
		this.tclass = tclass;
	}

	public Class<? extends ItemData> getTclass() {
		return tclass;
	}
}
