package jw.instruments.core.instuments;

import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.instruments.core.data.Consts;
import jw.instruments.core.data.CustomSkin;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.IgnoreInjection;
import jw.fluent.api.spigot.gameobjects.api.models.CustomModel;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

@IgnoreInjection
public class CustomInstrument implements Instrument {

    private int customModelId = 0;
    private String name;
    private String namespaceName;
    private Instrument parent;
    private CustomSkin customSkin;

    public CustomInstrument(CustomSkin customSkin, Instrument parent) {
        this.customModelId = customSkin.getCustomModelId();
        this.name = customSkin.getName().replace(" ", "-");
        this.namespaceName = this.name;
        this.parent = parent;
        this.customSkin = customSkin;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public NamespacedKey getNamespaceKey() {
        return new NamespacedKey(FluentApi.plugin(), namespaceName + "_custom_guitar");
    }

    @Override
    public ShapedRecipe getRecipe() {
        var recipe = new ShapedRecipe(getNamespaceKey(), getCustomModel().getItemStack());
        recipe.shape("XX2", "32X", "33X");
        recipe.setIngredient('X', Material.AIR);
        // recipe.setIngredient('1', Material.WOODEN_SHOVEL);
        recipe.setIngredient('2', Material.STICK);
        recipe.setIngredient('3', customSkin.getMaterial());
        return recipe;
    }

    @Override
    public CustomModel getCustomModel() {
        var model = new CustomModel(Consts.MODEL_MATERIAL, customModelId);
        model.setName(getName());
        model.addProperty(PersistentDataType.STRING, NAMESPACE, getName());
        return model;
    }
}
