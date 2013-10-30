/*
 * SonarQube IntelliJ
 * Copyright (C) 2013 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.ide.intellij.config;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sonar.ide.intellij.model.SonarQubeServer;
import org.sonar.ide.intellij.util.SonarQubeBundle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SonarQubeServerDialog extends DialogWrapper {
  private JPanel contentPane;
  private JTextField idTextField;
  private JTextField urlTextField;

  private SonarQubeServer server = null;
  private boolean edit = false;

  public JTextField getUrlTextField() {
    return urlTextField;
  }

  public JTextField getIdTextField() {
    return idTextField;
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return contentPane;
  }

  public SonarQubeServerDialog(@NotNull Component parent) {
    super(parent, false);
    init();
    setTitle(SonarQubeBundle.message("sonarqube.settings.server.add.title"));
  }

  @Override
  protected ValidationInfo doValidate() {
    this.server = new SonarQubeServer();
    this.server.setId(idTextField.getText());
    this.server.setUrl(urlTextField.getText());
    if (edit) {
      return SonarQubeSettings.getInstance().validateServer(this.server, this);
    } else {
      return SonarQubeSettings.getInstance().validateNewServer(this.server, this);
    }
  }

  public void setServer(SonarQubeServer server) {
    this.server = server;
    if (server == null) {
      setTitle(SonarQubeBundle.message("sonarqube.settings.server.add.title"));
      idTextField.setText("");
      urlTextField.setText("");
      edit = false;
    } else {
      setTitle(SonarQubeBundle.message("sonarqube.settings.server.edit.title"));
      idTextField.setText(server.getId());
      idTextField.setEditable(false);
      urlTextField.setText(server.getUrl().toString());
      edit = true;
    }
  }

  public SonarQubeServer getServer() {
    return server;
  }

  @NotNull
  @Override
  protected Action[] createLeftSideActions() {
    return new Action[]{new AbstractAction(SonarQubeBundle.message("sonarqube.settings.server.test")) {
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO
      }
    }};
  }
}
