package com.iicsadog.blocksblocks.core.manager.element;

import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public class BlueprintElement {

    private String name;

    private String description;

    private ResourceLocation logo;

    private List<BuildingElement> buildings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResourceLocation getLogo() {
        return logo;
    }

    public void setLogo(ResourceLocation logo) {
        this.logo = logo;
    }

    public List<BuildingElement> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<BuildingElement> buildings) {
        this.buildings = buildings;
    }

    public static class BuildingElement {

        private String name;

        private String description;

        private ResourceLocation icon;

        private ResourceLocation location;

        private HutElement hut;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<HutElement> getHut() {
            return Optional.ofNullable(hut);
        }

        public void setHut(HutElement hut) {
            this.hut = hut;
        }

        public ResourceLocation getLocation() {
            return location;
        }

        public void setLocation(ResourceLocation location) {
            this.location = location;
        }

        public Optional<ResourceLocation> getIcon() {
            return Optional.ofNullable(icon);
        }

        public void setIcon(ResourceLocation icon) {
            this.icon = icon;
        }
    }

    public static class HutElement {

        private int level;

        private ResourceLocation type;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public ResourceLocation getType() {
            return type;
        }

        public void setType(ResourceLocation type) {
            this.type = type;
        }
    }

}
