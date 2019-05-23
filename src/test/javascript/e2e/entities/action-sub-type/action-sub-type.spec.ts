/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ActionSubTypeComponentsPage, ActionSubTypeDeleteDialog, ActionSubTypeUpdatePage } from './action-sub-type.page-object';

const expect = chai.expect;

describe('ActionSubType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let actionSubTypeUpdatePage: ActionSubTypeUpdatePage;
    let actionSubTypeComponentsPage: ActionSubTypeComponentsPage;
    let actionSubTypeDeleteDialog: ActionSubTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ActionSubTypes', async () => {
        await navBarPage.goToEntity('action-sub-type');
        actionSubTypeComponentsPage = new ActionSubTypeComponentsPage();
        expect(await actionSubTypeComponentsPage.getTitle()).to.eq('cwcrm2App.actionSubType.home.title');
    });

    it('should load create ActionSubType page', async () => {
        await actionSubTypeComponentsPage.clickOnCreateButton();
        actionSubTypeUpdatePage = new ActionSubTypeUpdatePage();
        expect(await actionSubTypeUpdatePage.getPageTitle()).to.eq('cwcrm2App.actionSubType.home.createOrEditLabel');
        await actionSubTypeUpdatePage.cancel();
    });

    it('should create and save ActionSubTypes', async () => {
        const nbButtonsBeforeCreate = await actionSubTypeComponentsPage.countDeleteButtons();

        await actionSubTypeComponentsPage.clickOnCreateButton();
        await promise.all([actionSubTypeUpdatePage.setNameInput('name'), actionSubTypeUpdatePage.subTypeOfSelectLastOption()]);
        expect(await actionSubTypeUpdatePage.getNameInput()).to.eq('name');
        await actionSubTypeUpdatePage.save();
        expect(await actionSubTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await actionSubTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ActionSubType', async () => {
        const nbButtonsBeforeDelete = await actionSubTypeComponentsPage.countDeleteButtons();
        await actionSubTypeComponentsPage.clickOnLastDeleteButton();

        actionSubTypeDeleteDialog = new ActionSubTypeDeleteDialog();
        expect(await actionSubTypeDeleteDialog.getDialogTitle()).to.eq('cwcrm2App.actionSubType.delete.question');
        await actionSubTypeDeleteDialog.clickOnConfirmButton();

        expect(await actionSubTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
