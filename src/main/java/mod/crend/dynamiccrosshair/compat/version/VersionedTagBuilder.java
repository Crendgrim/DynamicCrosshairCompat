package mod.crend.dynamiccrosshair.compat.version;

import net.minecraft.registry.tag.TagBuilder;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

//? if <1.21.6 {
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
//?} else {
/*import net.minecraft.data.tag.ProvidedTagBuilder;
*///?}

public class VersionedTagBuilder<T> {
	//? if <1.21.6 {
	FabricTagProvider<T>.FabricTagBuilder parent;
	//?} else {
	/*TagBuilder tagBuilder;
	ProvidedTagBuilder<T, T> parent;
	*///?}

	//? if <1.21.6 {
	public VersionedTagBuilder(FabricTagProvider<T>.FabricTagBuilder parent) {
	//?} else {
	/*public VersionedTagBuilder(TagBuilder tagBuilder, ProvidedTagBuilder<T, T> parent) {
		this.tagBuilder = tagBuilder;
	*///?}
		this.parent = parent;
	}

	public VersionedTagBuilder<T> add(T value) {
		parent.add(value);
		return this;
	}

	@SafeVarargs
	public final VersionedTagBuilder<T> add(T... values) {
		return add(Arrays.stream(values));
	}


	public VersionedTagBuilder<T> add(Collection<T> values) {
		values.forEach(this::add);
		return this;
	}

	 public VersionedTagBuilder<T> add(Stream<T> values) {
		values.forEach(this::add);
		return this;
	}

	// Skip add(RegistryKey<T> registryKey) because >=1.21.6 does not have an equivalent
	// Skip addOptional(T value) because <1.21.6 does not have an equivalent

	public VersionedTagBuilder<T> addTag(TagKey<T> tag) {
		parent.addTag(tag);
		return this;
	}

	public VersionedTagBuilder<T> addOptionalTag(TagKey<T> tag) {
		parent.addOptionalTag(tag);
		return this;
	}

	public VersionedTagBuilder<T> addOptional(Identifier identifier) {
		//? if <1.21.6 {
		parent.addOptional(identifier);
		//?} else {
		/*tagBuilder.addOptional(identifier);
		*///?}
		return this;
	}

	public VersionedTagBuilder<T> setReplace(boolean replace) {
		parent.setReplace(replace);
		return this;
	}
}
