package org.multibit.hd.ui.views.wizards.welcome;

import net.miginfocom.swing.MigLayout;
import org.multibit.hd.core.api.MessageKey;
import org.multibit.hd.core.services.CoreServices;
import org.multibit.hd.ui.i18n.Languages;
import org.multibit.hd.ui.views.components.*;
import org.multibit.hd.ui.views.components.seed_phrase_display.SeedPhraseDisplayModel;
import org.multibit.hd.ui.views.components.seed_phrase_display.SeedPhraseDisplayView;
import org.multibit.hd.ui.views.wizards.AbstractWizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * <p>Wizard to provide the following to UI:</p>
 * <ul>
 * <li>Send bitcoin: Show send progress</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */

public class CreateWalletPanel extends JPanel {

  private static final Logger log = LoggerFactory.getLogger(WelcomePanel.class);

  private final AbstractWizard wizard;

  private final ModelAndView<SeedPhraseDisplayModel, SeedPhraseDisplayView> seedPhraseMaV;

  /**
   * The "previous" action
   */
  private Action previousAction = new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
      wizard.previous();
    }
  };

  /**
   * The "next" action
   */
  private Action nextAction = new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
      wizard.next();
    }
  };

  /**
   * @param wizard The wizard managing the states
   */
  public CreateWalletPanel(AbstractWizard wizard) {

    this.wizard = wizard;
    this.seedPhraseMaV = Components.newSeedPhraseMaV(CoreServices.newSeedPhraseGenerator());

    PanelDecorator.applyWizardTheme(this, wizardComponents(), MessageKey.CREATE_WALLET_TITLE);

    // Swap buttons to maintain reading order
    if (Languages.isLeftToRight()) {
      if (wizard.isExiting()) {
        add(Buttons.newExitButton(wizard.getExitAction()), "span 2,push");
      } else {
        add(Buttons.newCancelButton(wizard.getCancelAction()), "span 2,push");
      }
      add(Buttons.newPreviousButton(previousAction), "right,shrink");
      add(Buttons.newNextButton(nextAction), "right,shrink");
    } else {
      add(Buttons.newNextButton(nextAction), "left,push");
      add(Buttons.newPreviousButton(previousAction), "left,push");
      if (wizard.isExiting()) {
        add(Buttons.newExitButton(wizard.getExitAction()), "span 2,shrink");
      } else {
        add(Buttons.newCancelButton(wizard.getCancelAction()), "span 2,shrink");
      }
    }


  }

  private JPanel wizardComponents() {

    JPanel panel = Panels.newPanel(new MigLayout(
      "fill,ins 0", // Layout constrains
      "[]", // Column constraints
      "[]10[]" // Row constraints
    ));

    panel.add(seedPhraseMaV.getView().newPanel(), "wrap");
    panel.add(Panels.newSeedPhraseWarning(), "wrap");

    return panel;
  }

}
