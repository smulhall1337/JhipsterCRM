/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ActionTypeComponentsPage, ActionTypeDeleteDialog, ActionTypeUpdatePage } from './action-type.page-object';

const expect = chai.expect;

describe('ActionType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let actionTypeUpdatePage: ActionTypeUpdatePage;
    let actionTypeComponentsPage: ActionTypeComponentsPage;
    let actionTypeDeleteDialog: ActionTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ActionTypes', async () => {
        await navBarPage.goToEntity('action-type');
        actionTypeComponentsPage = new ActionTypeComponentsPage();
        expect(await actionTypeComponentsPage.getTitle()).to.eq('cwcrm2App.actionType.home.title');
    });

    it('should load create ActionType page', async () => {
        await actionTypeComponentsPage.clickOnCreateButton();
        actionTypeUpdatePage = new ActionTypeUpdatePage();
        expect(await actionTypeUpdatePage.getPageTitle()).to.eq('cwcrm2App.actionType.home.createOrEditLabel');
        await actionTypeUpdatePage.cancel();
    });

    it('should create and save ActionTypes', async () => {
        const nbButtonsBeforeCreate = await actionTypeComponentsPage.countDeleteButtons();

        await actionTypeComponentsPage.clickOnCreateButton();
        await promise.all([actionTypeUpdatePage.setNameInput('name')]);
        expect(await actionTypeUpdatePage.getNameInput()).to.eq('name');
        await actionTypeUpdatePage.save();
        expect(await actionTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await actionTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ActionType', async () => {
        const nbButtonsBeforeDelete = await actionTypeComponentsPage.countDeleteButtons();
        await actionTypeComponentsPage.clickOnLastDeleteButton();

        actionTypeDeleteDialog = new ActionTypeDeleteDialog();
        expect(await actionTypeDeleteDialog.getDialogTitle()).to.eq('cwcrm2App.actionType.delete.question');
        await actionTypeDeleteDialog.clickOnConfirmButton();

        expect(await actionTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
