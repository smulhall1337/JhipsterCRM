/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ReferralSourceComponentsPage, ReferralSourceDeleteDialog, ReferralSourceUpdatePage } from './referral-source.page-object';

const expect = chai.expect;

describe('ReferralSource e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let referralSourceUpdatePage: ReferralSourceUpdatePage;
    let referralSourceComponentsPage: ReferralSourceComponentsPage;
    let referralSourceDeleteDialog: ReferralSourceDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ReferralSources', async () => {
        await navBarPage.goToEntity('referral-source');
        referralSourceComponentsPage = new ReferralSourceComponentsPage();
        expect(await referralSourceComponentsPage.getTitle()).to.eq('cwcrm2App.referralSource.home.title');
    });

    it('should load create ReferralSource page', async () => {
        await referralSourceComponentsPage.clickOnCreateButton();
        referralSourceUpdatePage = new ReferralSourceUpdatePage();
        expect(await referralSourceUpdatePage.getPageTitle()).to.eq('cwcrm2App.referralSource.home.createOrEditLabel');
        await referralSourceUpdatePage.cancel();
    });

    it('should create and save ReferralSources', async () => {
        const nbButtonsBeforeCreate = await referralSourceComponentsPage.countDeleteButtons();

        await referralSourceComponentsPage.clickOnCreateButton();
        await promise.all([referralSourceUpdatePage.setNameInput('name')]);
        expect(await referralSourceUpdatePage.getNameInput()).to.eq('name');
        await referralSourceUpdatePage.save();
        expect(await referralSourceUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await referralSourceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ReferralSource', async () => {
        const nbButtonsBeforeDelete = await referralSourceComponentsPage.countDeleteButtons();
        await referralSourceComponentsPage.clickOnLastDeleteButton();

        referralSourceDeleteDialog = new ReferralSourceDeleteDialog();
        expect(await referralSourceDeleteDialog.getDialogTitle()).to.eq('cwcrm2App.referralSource.delete.question');
        await referralSourceDeleteDialog.clickOnConfirmButton();

        expect(await referralSourceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
