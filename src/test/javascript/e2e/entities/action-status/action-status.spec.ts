/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ActionStatusComponentsPage, ActionStatusDeleteDialog, ActionStatusUpdatePage } from './action-status.page-object';

const expect = chai.expect;

describe('ActionStatus e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let actionStatusUpdatePage: ActionStatusUpdatePage;
    let actionStatusComponentsPage: ActionStatusComponentsPage;
    let actionStatusDeleteDialog: ActionStatusDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ActionStatuses', async () => {
        await navBarPage.goToEntity('action-status');
        actionStatusComponentsPage = new ActionStatusComponentsPage();
        expect(await actionStatusComponentsPage.getTitle()).to.eq('cwcrm2App.actionStatus.home.title');
    });

    it('should load create ActionStatus page', async () => {
        await actionStatusComponentsPage.clickOnCreateButton();
        actionStatusUpdatePage = new ActionStatusUpdatePage();
        expect(await actionStatusUpdatePage.getPageTitle()).to.eq('cwcrm2App.actionStatus.home.createOrEditLabel');
        await actionStatusUpdatePage.cancel();
    });

    it('should create and save ActionStatuses', async () => {
        const nbButtonsBeforeCreate = await actionStatusComponentsPage.countDeleteButtons();

        await actionStatusComponentsPage.clickOnCreateButton();
        await promise.all([actionStatusUpdatePage.setNameInput('name')]);
        expect(await actionStatusUpdatePage.getNameInput()).to.eq('name');
        await actionStatusUpdatePage.save();
        expect(await actionStatusUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await actionStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ActionStatus', async () => {
        const nbButtonsBeforeDelete = await actionStatusComponentsPage.countDeleteButtons();
        await actionStatusComponentsPage.clickOnLastDeleteButton();

        actionStatusDeleteDialog = new ActionStatusDeleteDialog();
        expect(await actionStatusDeleteDialog.getDialogTitle()).to.eq('cwcrm2App.actionStatus.delete.question');
        await actionStatusDeleteDialog.clickOnConfirmButton();

        expect(await actionStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
