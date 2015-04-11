/*
 * Copyright (c) 2009-2012 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.asset;

import java.io.IOException;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;

/**
 * Blender key. Contains path of the blender file and its loading properties.
 * @author Marcin Roguski (Kaelthas)
 */
public class BlenderKey extends ModelKey {
    protected static final int         DEFAULT_FPS               = 25;
    /**
     * FramesPerSecond parameter describe how many frames there are in each second. It allows to calculate the time
     * between the frames.
     */
    protected int                      fps                       = DEFAULT_FPS;
    /**
     * This variable is a bitwise flag of FeatureToLoad interface values; By default everything is being loaded.
     */
    protected int                      featuresToLoad            = FeaturesToLoad.ALL;
    /** The variable that tells if content of the file (along with data unlinked to any feature on the scene) should be stored as 'user data' in the result spatial. */
    protected boolean                  loadUnlinkedAssets;
    /** The root path for all the assets. */
    protected String                   assetRootPath;
    /** This variable indicate if Y axis is UP axis. If not then Z is up. By default set to true. */
    protected boolean                  fixUpAxis                 = true;
    /** Generated textures resolution (PPU - Pixels Per Unit). */
    protected int                      generatedTexturePPU       = 128;
    /**
     * The name of world settings that the importer will use. If not set or specified name does not occur in the file
     * then the first world settings in the file will be used.
     */
    protected String                   usedWorld;
    /**
     * User's default material that is set fo objects that have no material definition in blender. The default value is
     * null. If the value is null the importer will use its own default material (gray color - like in blender).
     */
    protected Material                 defaultMaterial;
    /** Face cull mode. By default it is disabled. */
    protected FaceCullMode             faceCullMode              = FaceCullMode.Back;
    /**
     * Variable describes which layers will be loaded. N-th bit set means N-th layer will be loaded.
     * If set to -1 then the current layer will be loaded.
     */
    protected int                      layersToLoad              = -1;
    /** A variable that toggles the object custom properties loading. */
    protected boolean                  loadObjectProperties      = true;
    /**
     * Maximum texture size. Might be dependant on the graphic card.
     * This value is taken from <b>org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE</b>.
     */
    protected int                      maxTextureSize            = 8192;
    /** Allows to toggle generated textures loading. Disabled by default because it very often takes too much memory and needs to be used wisely. */
    protected boolean                  loadGeneratedTextures;
    /** Tells if the mipmaps will be generated by jme or not. By default generation is dependant on the blender settings. */
    protected MipmapGenerationMethod   mipmapGenerationMethod    = MipmapGenerationMethod.GENERATE_WHEN_NEEDED;
    /**
     * If the sky has only generated textures applied then they will have the following size (both width and height). If 2d textures are used then the generated
     * textures will get their proper size.
     */
    protected int                      skyGeneratedTextureSize   = 1000;
    /** The radius of a shape that will be used while creating the generated texture for the sky. The higher it is the larger part of the texture will be seen. */
    protected float                    skyGeneratedTextureRadius = 1;
    /** The shape against which the generated texture for the sky will be created. */
    protected SkyGeneratedTextureShape skyGeneratedTextureShape  = SkyGeneratedTextureShape.SPHERE;
    /**
     * This field tells if the importer should optimise the use of textures or not. If set to true, then textures of the same mapping type will be merged together
     * and textures that in the final result will never be visible - will be discarded.
     */
    protected boolean                  optimiseTextures;
    /** The method of matching animations to skeletons. The default value is: AT_LEAST_ONE_NAME_MATCH. */
    protected AnimationMatchMethod     animationMatchMethod      = AnimationMatchMethod.AT_LEAST_ONE_NAME_MATCH;
    /** The size of points that are loaded and do not belong to any edge of the mesh. */
    protected float                    pointsSize                = 1;
    /** The width of edges that are loaded from the mesh and do not belong to any face. */
    protected float                    linesWidth                = 1;

    /**
     * Constructor used by serialization mechanisms.
     */
    public BlenderKey() {
    }

    /**
     * Constructor. Creates a key for the given file name.
     * @param name
     *            the name (path) of a file
     */
    public BlenderKey(String name) {
        super(name);
    }

    /**
     * This method returns frames per second amount. The default value is BlenderKey.DEFAULT_FPS = 25.
     * @return the frames per second amount
     */
    public int getFps() {
        return fps;
    }

    /**
     * This method sets frames per second amount.
     * @param fps
     *            the frames per second amount
     */
    public void setFps(int fps) {
        this.fps = fps;
    }

    /**
     * This method returns the face cull mode.
     * @return the face cull mode
     */
    public FaceCullMode getFaceCullMode() {
        return faceCullMode;
    }

    /**
     * This method sets the face cull mode.
     * @param faceCullMode
     *            the face cull mode
     */
    public void setFaceCullMode(FaceCullMode faceCullMode) {
        this.faceCullMode = faceCullMode;
    }

    /**
     * This method sets layers to be loaded.
     * @param layersToLoad
     *            layers to be loaded
     */
    public void setLayersToLoad(int layersToLoad) {
        this.layersToLoad = layersToLoad;
    }

    /**
     * This method returns layers to be loaded.
     * @return layers to be loaded
     */
    public int getLayersToLoad() {
        return layersToLoad;
    }

    /**
     * This method sets the properies loading policy.
     * By default the value is true.
     * @param loadObjectProperties
     *            true to load properties and false to suspend their loading
     */
    public void setLoadObjectProperties(boolean loadObjectProperties) {
        this.loadObjectProperties = loadObjectProperties;
    }

    /**
     * @return the current properties loading properties
     */
    public boolean isLoadObjectProperties() {
        return loadObjectProperties;
    }

    /**
     * The default value for this parameter is the same as defined by: org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE.
     * If by any means this is too large for user's hardware configuration use the 'setMaxTextureSize' method to change that.
     * @return maximum texture size (width/height)
     */
    public int getMaxTextureSize() {
        return maxTextureSize;
    }

    /**
     * This method sets the maximum texture size.
     * @param maxTextureSize
     *            the maximum texture size
     */
    public void setMaxTextureSize(int maxTextureSize) {
        this.maxTextureSize = maxTextureSize;
    }

    /**
     * This method sets the flag that toggles the generated textures loading.
     * @param loadGeneratedTextures
     *            <b>true</b> if generated textures should be loaded and <b>false</b> otherwise
     */
    public void setLoadGeneratedTextures(boolean loadGeneratedTextures) {
        this.loadGeneratedTextures = loadGeneratedTextures;
    }

    /**
     * @return tells if the generated textures should be loaded (<b>false</b> is the default value)
     */
    public boolean isLoadGeneratedTextures() {
        return loadGeneratedTextures;
    }

    /**
     * This method sets the asset root path.
     * @param assetRootPath
     *            the assets root path
     */
    public void setAssetRootPath(String assetRootPath) {
        this.assetRootPath = assetRootPath;
    }

    /**
     * This method returns the asset root path.
     * @return the asset root path
     */
    public String getAssetRootPath() {
        return assetRootPath;
    }

    /**
     * This method adds features to be loaded.
     * @param featuresToLoad
     *            bitwise flag of FeaturesToLoad interface values
     */
    @Deprecated
    public void includeInLoading(int featuresToLoad) {
        this.featuresToLoad |= featuresToLoad;
    }

    /**
     * This method removes features from being loaded.
     * @param featuresNotToLoad
     *            bitwise flag of FeaturesToLoad interface values
     */
    @Deprecated
    public void excludeFromLoading(int featuresNotToLoad) {
        featuresToLoad &= ~featuresNotToLoad;
    }

    @Deprecated
    public boolean shouldLoad(int featureToLoad) {
        return (featuresToLoad & featureToLoad) != 0;
    }

    /**
     * This method returns bitwise value of FeaturesToLoad interface value. It describes features that will be loaded by
     * the blender file loader.
     * @return features that will be loaded by the blender file loader
     */
    @Deprecated
    public int getFeaturesToLoad() {
        return featuresToLoad;
    }

    /**
     * This method determines if unlinked assets should be loaded.
     * If not then only objects on selected layers will be loaded and their assets if required.
     * If yes then all assets will be loaded even if they are on inactive layers or are not linked
     * to anything.
     * @return <b>true</b> if unlinked assets should be loaded and <b>false</b> otherwise
     */
    public boolean isLoadUnlinkedAssets() {
        return loadUnlinkedAssets;
    }

    /**
     * This method sets if unlinked assets should be loaded.
     * If not then only objects on selected layers will be loaded and their assets if required.
     * If yes then all assets will be loaded even if they are on inactive layers or are not linked
     * to anything.
     * @param loadUnlinkedAssets
     *            <b>true</b> if unlinked assets should be loaded and <b>false</b> otherwise
     */
    public void setLoadUnlinkedAssets(boolean loadUnlinkedAssets) {
        this.loadUnlinkedAssets = loadUnlinkedAssets;
    }

    /**
     * This method sets the fix up axis state. If set to true then Y is up axis. Otherwise the up i Z axis. By default Y
     * is up axis.
     * @param fixUpAxis
     *            the up axis state variable
     */
    public void setFixUpAxis(boolean fixUpAxis) {
        this.fixUpAxis = fixUpAxis;
    }

    /**
     * This method returns the fix up axis state. If set to true then Y is up axis. Otherwise the up i Z axis. By
     * default Y is up axis.
     * @return the up axis state variable
     */
    public boolean isFixUpAxis() {
        return fixUpAxis;
    }

    /**
     * This method sets the generated textures resolution.
     * @param generatedTexturePPU
     *            the generated textures resolution
     */
    public void setGeneratedTexturePPU(int generatedTexturePPU) {
        this.generatedTexturePPU = generatedTexturePPU;
    }

    /**
     * @return the generated textures resolution
     */
    public int getGeneratedTexturePPU() {
        return generatedTexturePPU;
    }

    /**
     * @return mipmaps generation method
     */
    public MipmapGenerationMethod getMipmapGenerationMethod() {
        return mipmapGenerationMethod;
    }

    /**
     * @param mipmapGenerationMethod
     *            mipmaps generation method
     */
    public void setMipmapGenerationMethod(MipmapGenerationMethod mipmapGenerationMethod) {
        this.mipmapGenerationMethod = mipmapGenerationMethod;
    }

    /**
     * @return the size of the generated textures for the sky (used if no flat textures are applied)
     */
    public int getSkyGeneratedTextureSize() {
        return skyGeneratedTextureSize;
    }

    /**
     * @param skyGeneratedTextureSize
     *            the size of the generated textures for the sky (used if no flat textures are applied)
     */
    public void setSkyGeneratedTextureSize(int skyGeneratedTextureSize) {
        if (skyGeneratedTextureSize <= 0) {
            throw new IllegalArgumentException("The texture size must be a positive value (the value given as a parameter: " + skyGeneratedTextureSize + ")!");
        }
        this.skyGeneratedTextureSize = skyGeneratedTextureSize;
    }

    /**
     * @return the radius of a shape that will be used while creating the generated texture for the sky, the higher it is the larger part of the texture will be seen
     */
    public float getSkyGeneratedTextureRadius() {
        return skyGeneratedTextureRadius;
    }

    /**
     * @param skyGeneratedTextureRadius
     *            the radius of a shape that will be used while creating the generated texture for the sky, the higher it is the larger part of the texture will be seen
     */
    public void setSkyGeneratedTextureRadius(float skyGeneratedTextureRadius) {
        this.skyGeneratedTextureRadius = skyGeneratedTextureRadius;
    }

    /**
     * @return the shape against which the generated texture for the sky will be created (by default it is a sphere).
     */
    public SkyGeneratedTextureShape getSkyGeneratedTextureShape() {
        return skyGeneratedTextureShape;
    }

    /**
     * @param skyGeneratedTextureShape
     *            the shape against which the generated texture for the sky will be created
     */
    public void setSkyGeneratedTextureShape(SkyGeneratedTextureShape skyGeneratedTextureShape) {
        if (skyGeneratedTextureShape == null) {
            throw new IllegalArgumentException("The sky generated shape type cannot be null!");
        }
        this.skyGeneratedTextureShape = skyGeneratedTextureShape;
    }

    /**
     * If set to true, then textures of the same mapping type will be merged together
     * and textures that in the final result will never be visible - will be discarded.
     * @param optimiseTextures
     *            the variable that tells if the textures should be optimised or not
     */
    public void setOptimiseTextures(boolean optimiseTextures) {
        this.optimiseTextures = optimiseTextures;
    }

    /**
     * @return the variable that tells if the textures should be optimised or not (by default the optimisation is disabled)
     */
    public boolean isOptimiseTextures() {
        return optimiseTextures;
    }

    /**
     * Sets the way the animations will be matched with skeletons.
     * 
     * @param animationMatchMethod
     *            the way the animations will be matched with skeletons
     */
    public void setAnimationMatchMethod(AnimationMatchMethod animationMatchMethod) {
        this.animationMatchMethod = animationMatchMethod;
    }

    /**
     * @return the way the animations will be matched with skeletons
     */
    public AnimationMatchMethod getAnimationMatchMethod() {
        return animationMatchMethod;
    }

    /**
     * @return the size of points that are loaded and do not belong to any edge of the mesh
     */
    public float getPointsSize() {
        return pointsSize;
    }

    /**
     * Sets the size of points that are loaded and do not belong to any edge of the mesh.
     * @param pointsSize
     *            The size of points that are loaded and do not belong to any edge of the mesh
     */
    public void setPointsSize(float pointsSize) {
        this.pointsSize = pointsSize;
    }

    /**
     * @return the width of edges that are loaded from the mesh and do not belong to any face
     */
    public float getLinesWidth() {
        return linesWidth;
    }

    /**
     * Sets the width of edges that are loaded from the mesh and do not belong to any face.
     * @param linesWidth
     *            the width of edges that are loaded from the mesh and do not belong to any face
     */
    public void setLinesWidth(float linesWidth) {
        this.linesWidth = linesWidth;
    }

    /**
     * This mehtod sets the name of the WORLD data block taht should be used during file loading. By default the name is
     * not set. If no name is set or the given name does not occur in the file - the first WORLD data block will be used
     * during loading (assumin any exists in the file).
     * @param usedWorld
     *            the name of the WORLD block used during loading
     */
    public void setUsedWorld(String usedWorld) {
        this.usedWorld = usedWorld;
    }

    /**
     * This mehtod returns the name of the WORLD data block taht should be used during file loading.
     * @return the name of the WORLD block used during loading
     */
    public String getUsedWorld() {
        return usedWorld;
    }

    /**
     * This method sets the default material for objects.
     * @param defaultMaterial
     *            the default material
     */
    public void setDefaultMaterial(Material defaultMaterial) {
        this.defaultMaterial = defaultMaterial;
    }

    /**
     * This method returns the default material.
     * @return the default material
     */
    public Material getDefaultMaterial() {
        return defaultMaterial;
    }

    @Override
    public void write(JmeExporter e) throws IOException {
        super.write(e);
        OutputCapsule oc = e.getCapsule(this);
        oc.write(fps, "fps", DEFAULT_FPS);
        oc.write(featuresToLoad, "features-to-load", FeaturesToLoad.ALL);
        oc.write(loadUnlinkedAssets, "load-unlinked-assets", false);
        oc.write(assetRootPath, "asset-root-path", null);
        oc.write(fixUpAxis, "fix-up-axis", true);
        oc.write(generatedTexturePPU, "generated-texture-ppu", 128);
        oc.write(usedWorld, "used-world", null);
        oc.write(defaultMaterial, "default-material", null);
        oc.write(faceCullMode, "face-cull-mode", FaceCullMode.Off);
        oc.write(layersToLoad, "layers-to-load", -1);
        oc.write(mipmapGenerationMethod, "mipmap-generation-method", MipmapGenerationMethod.GENERATE_WHEN_NEEDED);
        oc.write(skyGeneratedTextureSize, "sky-generated-texture-size", 1000);
        oc.write(skyGeneratedTextureRadius, "sky-generated-texture-radius", 1f);
        oc.write(skyGeneratedTextureShape, "sky-generated-texture-shape", SkyGeneratedTextureShape.SPHERE);
        oc.write(optimiseTextures, "optimise-textures", false);
        oc.write(animationMatchMethod, "animation-match-method", AnimationMatchMethod.AT_LEAST_ONE_NAME_MATCH);
        oc.write(pointsSize, "points-size", 1);
        oc.write(linesWidth, "lines-width", 1);
    }

    @Override
    public void read(JmeImporter e) throws IOException {
        super.read(e);
        InputCapsule ic = e.getCapsule(this);
        fps = ic.readInt("fps", DEFAULT_FPS);
        featuresToLoad = ic.readInt("features-to-load", FeaturesToLoad.ALL);
        loadUnlinkedAssets = ic.readBoolean("load-unlinked-assets", false);
        assetRootPath = ic.readString("asset-root-path", null);
        fixUpAxis = ic.readBoolean("fix-up-axis", true);
        generatedTexturePPU = ic.readInt("generated-texture-ppu", 128);
        usedWorld = ic.readString("used-world", null);
        defaultMaterial = (Material) ic.readSavable("default-material", null);
        faceCullMode = ic.readEnum("face-cull-mode", FaceCullMode.class, FaceCullMode.Off);
        layersToLoad = ic.readInt("layers-to=load", -1);
        mipmapGenerationMethod = ic.readEnum("mipmap-generation-method", MipmapGenerationMethod.class, MipmapGenerationMethod.GENERATE_WHEN_NEEDED);
        skyGeneratedTextureSize = ic.readInt("sky-generated-texture-size", 1000);
        skyGeneratedTextureRadius = ic.readFloat("sky-generated-texture-radius", 1f);
        skyGeneratedTextureShape = ic.readEnum("sky-generated-texture-shape", SkyGeneratedTextureShape.class, SkyGeneratedTextureShape.SPHERE);
        optimiseTextures = ic.readBoolean("optimise-textures", false);
        animationMatchMethod = ic.readEnum("animation-match-method", AnimationMatchMethod.class, AnimationMatchMethod.AT_LEAST_ONE_NAME_MATCH);
        pointsSize = ic.readFloat("points-size", 1);
        linesWidth = ic.readFloat("lines-width", 1);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (animationMatchMethod == null ? 0 : animationMatchMethod.hashCode());
        result = prime * result + (assetRootPath == null ? 0 : assetRootPath.hashCode());
        result = prime * result + (defaultMaterial == null ? 0 : defaultMaterial.hashCode());
        result = prime * result + (faceCullMode == null ? 0 : faceCullMode.hashCode());
        result = prime * result + featuresToLoad;
        result = prime * result + (fixUpAxis ? 1231 : 1237);
        result = prime * result + fps;
        result = prime * result + generatedTexturePPU;
        result = prime * result + layersToLoad;
        result = prime * result + (loadGeneratedTextures ? 1231 : 1237);
        result = prime * result + (loadObjectProperties ? 1231 : 1237);
        result = prime * result + (loadUnlinkedAssets ? 1231 : 1237);
        result = prime * result + maxTextureSize;
        result = prime * result + (mipmapGenerationMethod == null ? 0 : mipmapGenerationMethod.hashCode());
        result = prime * result + (optimiseTextures ? 1231 : 1237);
        result = prime * result + Float.floatToIntBits(skyGeneratedTextureRadius);
        result = prime * result + (skyGeneratedTextureShape == null ? 0 : skyGeneratedTextureShape.hashCode());
        result = prime * result + skyGeneratedTextureSize;
        result = prime * result + (usedWorld == null ? 0 : usedWorld.hashCode());
        result = prime * result + (int) pointsSize;
        result = prime * result + (int) linesWidth;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlenderKey) {
            return false;
        }
        BlenderKey other = (BlenderKey) obj;
        if (animationMatchMethod != other.animationMatchMethod) {
            return false;
        }
        if (assetRootPath == null) {
            if (other.assetRootPath != null) {
                return false;
            }
        } else if (!assetRootPath.equals(other.assetRootPath)) {
            return false;
        }
        if (defaultMaterial == null) {
            if (other.defaultMaterial != null) {
                return false;
            }
        } else if (!defaultMaterial.equals(other.defaultMaterial)) {
            return false;
        }
        if (faceCullMode != other.faceCullMode) {
            return false;
        }
        if (featuresToLoad != other.featuresToLoad) {
            return false;
        }
        if (fixUpAxis != other.fixUpAxis) {
            return false;
        }
        if (fps != other.fps) {
            return false;
        }
        if (generatedTexturePPU != other.generatedTexturePPU) {
            return false;
        }
        if (layersToLoad != other.layersToLoad) {
            return false;
        }
        if (loadGeneratedTextures != other.loadGeneratedTextures) {
            return false;
        }
        if (loadObjectProperties != other.loadObjectProperties) {
            return false;
        }
        if (loadUnlinkedAssets != other.loadUnlinkedAssets) {
            return false;
        }
        if (maxTextureSize != other.maxTextureSize) {
            return false;
        }
        if (mipmapGenerationMethod != other.mipmapGenerationMethod) {
            return false;
        }
        if (optimiseTextures != other.optimiseTextures) {
            return false;
        }
        if (Float.floatToIntBits(skyGeneratedTextureRadius) != Float.floatToIntBits(other.skyGeneratedTextureRadius)) {
            return false;
        }
        if (skyGeneratedTextureShape != other.skyGeneratedTextureShape) {
            return false;
        }
        if (skyGeneratedTextureSize != other.skyGeneratedTextureSize) {
            return false;
        }
        if (usedWorld == null) {
            if (other.usedWorld != null) {
                return false;
            }
        } else if (!usedWorld.equals(other.usedWorld)) {
            return false;
        }
        if (pointsSize != other.pointsSize) {
            return false;
        }
        if (linesWidth != other.linesWidth) {
            return false;
        }
        return true;
    }

    /**
     * This enum tells the importer if the mipmaps for textures will be generated by jme. <li>NEVER_GENERATE and ALWAYS_GENERATE are quite understandable <li>GENERATE_WHEN_NEEDED is an option that checks if the texture had 'Generate mipmaps' option set in blender, mipmaps are generated only when the option is set
     * @author Marcin Roguski (Kaelthas)
     */
    public static enum MipmapGenerationMethod {
        NEVER_GENERATE, ALWAYS_GENERATE, GENERATE_WHEN_NEEDED;
    }

    /**
     * This interface describes the features of the scene that are to be loaded.
     * @deprecated this interface is deprecated and is not used anymore; to ensure the loading models consistency
     *             everything must be loaded because in blender one feature might depend on another
     * @author Marcin Roguski (Kaelthas)
     */
    @Deprecated
    public static interface FeaturesToLoad {

        int SCENES     = 0x0000FFFF;
        int OBJECTS    = 0x0000000B;
        int ANIMATIONS = 0x00000004;
        int MATERIALS  = 0x00000003;
        int TEXTURES   = 0x00000001;
        int CAMERAS    = 0x00000020;
        int LIGHTS     = 0x00000010;
        int WORLD      = 0x00000040;
        int ALL        = 0xFFFFFFFF;
    }

    /**
     * The shape againts which the sky generated texture will be created.
     * 
     * @author Marcin Roguski (Kaelthas)
     */
    public static enum SkyGeneratedTextureShape {
        CUBE, SPHERE;
    }

    /**
     * This enum describes which animations should be attached to which armature.
     * Blender does not store the mapping between action and armature. That is why the importer
     * will try to match those by comparing bone name of the armature with the channel names
     * int the actions.
     * 
     * @author Marcin Roguski (Kaelthas)
     */
    public static enum AnimationMatchMethod {
        /**
         * Animation is matched with skeleton when at leas one bone name matches the name of the action channel.
         * All the bones that do not have their corresponding channel in the animation will not get the proper tracks for
         * this particulat animation.
         * Also the channel will not be used for the animation if it does not find the proper bone name.
         */
        AT_LEAST_ONE_NAME_MATCH,
        /**
         * Animation is matched when all action names are covered by the target names (bone names or the name of the
         * animated spatial.
         */
        ALL_NAMES_MATCH;
    }
}
