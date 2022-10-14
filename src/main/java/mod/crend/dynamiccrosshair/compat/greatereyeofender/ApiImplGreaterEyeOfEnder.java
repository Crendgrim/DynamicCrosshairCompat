package mod.crend.dynamiccrosshair.compat.greatereyeofender;

import com.kwpugh.greater_eye.GreaterEye;
import com.kwpugh.greater_eye.items.ItemGreaterEye;
import com.kwpugh.greater_eye.items.ItemGreaterEyeEnd;
import com.kwpugh.greater_eye.items.ItemGreaterEyeNether;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplGreaterEyeOfEnder implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return GreaterEye.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof ItemGreaterEye || item instanceof ItemGreaterEyeNether || item instanceof ItemGreaterEyeEnd);
	}
}
