/*
 * Copyright 2012 Benjamin Glatzel <benjamin.glatzel@me.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.rendering.gui.windows;

import org.lwjgl.input.Keyboard;
import org.terasology.events.RespawnEvent;
import org.terasology.input.binds.PauseButton;
import org.terasology.game.CoreRegistry;
import org.terasology.game.GameEngine;
import org.terasology.game.modes.StateMainMenu;
import org.terasology.logic.LocalPlayer;
import org.terasology.asset.AssetManager;
import org.terasology.rendering.gui.components.UIButton;
import org.terasology.rendering.gui.components.UIText;
import org.terasology.rendering.gui.framework.UIDisplayElement;
import org.terasology.rendering.gui.framework.UIDisplayWindow;
import org.terasology.rendering.gui.framework.UIGraphicsElement;
import org.terasology.rendering.gui.framework.events.ClickListener;

import javax.vecmath.Vector2f;

/**
 * Simple pause menu providing buttons for respawning the player and creating a new world.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class UIMenuPause extends UIDisplayWindow {

    final UIGraphicsElement _title;

    final UIButton _exitButton;
    final UIButton _mainMenuButton;
    final UIButton _respawnButton;
    final UIButton _backToGameButton;

    final UIText _version;

    public UIMenuPause() {
        setBackgroundColor(0x00, 0x00, 0x00, 0.75f);
        setModal(true);
        setCloseBinds(new String[] {PauseButton.ID});
        setCloseKeys(new int[] {Keyboard.KEY_ESCAPE});
        maximize();
        
        _title = new UIGraphicsElement(AssetManager.loadTexture("engine:terasology"));
        _title.setVisible(true);
        _title.setSize(new Vector2f(512f, 128f));

        _version = new UIText("Pre Alpha");
        _version.setVisible(true);

        _exitButton = new UIButton(new Vector2f(256f, 32f), UIButton.eButtonType.NORMAL);
        _exitButton.getLabel().setText("Exit Terasology");
        _exitButton.setVisible(true);

        _exitButton.addClickListener(new ClickListener() {
            @Override
            public void click(UIDisplayElement element, int button) {
                CoreRegistry.get(GameEngine.class).shutdown();
            }
        });

        _respawnButton = new UIButton(new Vector2f(256f, 32f), UIButton.eButtonType.NORMAL);
        _respawnButton.getLabel().setText("Respawn");
        _respawnButton.setVisible(true);

        _respawnButton.addClickListener(new ClickListener() {
            @Override
            public void click(UIDisplayElement element, int button) {
                CoreRegistry.get(LocalPlayer.class).getEntity().send(new RespawnEvent());
                
                close();
            }
        });

        _mainMenuButton = new UIButton(new Vector2f(256f, 32f), UIButton.eButtonType.NORMAL);
        _mainMenuButton.getLabel().setText("Return to Main Menu");
        _mainMenuButton.setVisible(true);
        _mainMenuButton.addClickListener(new ClickListener() {
            @Override
            public void click(UIDisplayElement element, int button) {
                CoreRegistry.get(GameEngine.class).changeState(new StateMainMenu());
            }
        });

        _backToGameButton = new UIButton(new Vector2f(256f, 32f), UIButton.eButtonType.NORMAL);
        _backToGameButton.getLabel().setText("Back to game");
        _backToGameButton.setVisible(true);
        _backToGameButton.addClickListener(new ClickListener() {
            public void click(UIDisplayElement element, int button) {
                close();
            }
        });


        addDisplayElement(_title);
        addDisplayElement(_version);
        addDisplayElement(_exitButton);
        addDisplayElement(_respawnButton);
        addDisplayElement(_mainMenuButton);
        addDisplayElement(_backToGameButton);

        layout();
    }

    @Override
    public void layout() {
        super.layout();

        if (_version != null) {
            _version.centerHorizontally();
            _version.getPosition().y = 230f;

            _backToGameButton.centerHorizontally();
            _backToGameButton.getPosition().y = 300f;

            _respawnButton.centerHorizontally();
            _respawnButton.getPosition().y = 300f + 32f + 24f;
    
            _mainMenuButton.centerHorizontally();
            _mainMenuButton.getPosition().y = 300f + 2 * 32f + 24f + 4f;
    
            _exitButton.centerHorizontally();
            _exitButton.getPosition().y = 300f + 3 * 32f + 24f + 8f;
    
            _title.centerHorizontally();
            _title.getPosition().y = 128f;
        }
    }
}
