/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

/**
 *
 * @author Thiago Aderaldo Lessa
 */

/** Exemplo 6 - como dar a superficie do objeto um material e uma textura */
public class HelloMaterial extends SimpleApplication{

    public static void main(String[] args){
        HelloMaterial app = new HelloMaterial();
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
    
        /** Um simples cubo texturizado - com uma boa qualidade de mapa MIP*/
        Box cube1Mesh = new Box(1f, 1f, 1f);
        Geometry cube1Geo = new Geometry("My Textured Box", cube1Mesh);
        cube1Geo.setLocalTranslation(new Vector3f(-3f, 1.1f, 0));
        Material cube1Mat = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        Texture cube1Tex = assetManager.loadTexture("Interface/Logo/Monkey.jpg");
        cube1Mat.setTexture("ColorMap", cube1Tex);
        cube1Geo.setMaterial(cube1Mat);
        rootNode.attachChild(cube1Geo);
        
        /** Uma textura translúcida e transparente, semelhante a uma moldura de janela. */
        Box cube2Mesh = new Box(1f,1f,0.01f);
        Geometry cube2Geo = new Geometry("window frame",cube2Mesh);
        Material cube2Mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        cube2Mat.setTexture("ColorMap", 
                assetManager.loadTexture("Textures/ColoredTex/Monkey.png"));
        cube2Mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        cube2Geo.setQueueBucket(Bucket.Transparent);
        cube2Geo.setMaterial(cube2Mat);
        rootNode.attachChild(cube2Geo);
        
        /** Uma pedra irregular com um efeito de luz brilhante. */
        Sphere sphereMesh = new Sphere(32, 32, 2f);
        Geometry sphereGeo = new Geometry("Shiny rock",sphereMesh);
        sphereMesh.setTextureMode(Sphere.TextureMode.Projected);//melhor qualidade em esferas
        TangentBinormalGenerator.generate(sphereMesh); //para  o efeito de brilho
        Material sphereMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        sphereMat.setTexture("DiffuseMap",assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));
        sphereMat.setTexture("NormalMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond_normal.png"));
        sphereMat.setBoolean("UseMaterialColors", true);
        sphereMat.setColor("Diffuse", ColorRGBA.White);
        sphereMat.setColor("Specular", ColorRGBA.White);
        sphereMat.setFloat("Shininess", 64f); // [0,128]
        sphereGeo.setMaterial(sphereMat);
        sphereGeo.setLocalTranslation(0, 2, -2); //move um pouco
        sphereGeo.rotate(1.6f, 0, 0); //rotaciona um pouco
        rootNode.attachChild(sphereGeo);
        
        /** Deve-se adicionar luz para fazer com que o objeto se torne visível */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1, 0, -2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
    }
    
    
    
}
