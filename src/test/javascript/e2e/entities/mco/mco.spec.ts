/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MCOComponentsPage, MCODeleteDialog, MCOUpdatePage } from './mco.page-object';

const expect = chai.expect;

describe('MCO e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let mCOUpdatePage: MCOUpdatePage;
    let mCOComponentsPage: MCOComponentsPage;
    let mCODeleteDialog: MCODeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load MCOS', async () => {
        await navBarPage.goToEntity('mco');
        mCOComponentsPage = new MCOComponentsPage();
        expect(await mCOComponentsPage.getTitle()).to.eq('cwcrm2App.mCO.home.title');
    });

    it('should load create MCO page', async () => {
        await mCOComponentsPage.clickOnCreateButton();
        mCOUpdatePage = new MCOUpdatePage();
        expect(await mCOUpdatePage.getPageTitle()).to.eq('cwcrm2App.mCO.home.createOrEditLabel');
        await mCOUpdatePage.cancel();
    });

    it('should create and save MCOS', async () => {
        const nbButtonsBeforeCreate = await mCOComponentsPage.countDeleteButtons();

        await mCOComponentsPage.clickOnCreateButton();
        await promise.all([mCOUpdatePage.setNameInput('name')]);
        expect(await mCOUpdatePage.getNameInput()).to.eq('name');
        await mCOUpdatePage.save();
        expect(await mCOUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await mCOComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last MCO', async () => {
        const nbButtonsBeforeDelete = await mCOComponentsPage.countDeleteButtons();
        await mCOComponentsPage.clickOnLastDeleteButton();

        mCODeleteDialog = new MCODeleteDialog();
        expect(await mCODeleteDialog.getDialogTitle()).to.eq('cwcrm2App.mCO.delete.question');
        await mCODeleteDialog.clickOnConfirmButton();

        expect(await mCOComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
