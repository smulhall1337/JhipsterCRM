/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ReferralTypeComponentsPage, ReferralTypeDeleteDialog, ReferralTypeUpdatePage } from './referral-type.page-object';

const expect = chai.expect;

describe('ReferralType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let referralTypeUpdatePage: ReferralTypeUpdatePage;
    let referralTypeComponentsPage: ReferralTypeComponentsPage;
    let referralTypeDeleteDialog: ReferralTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ReferralTypes', async () => {
        await navBarPage.goToEntity('referral-type');
        referralTypeComponentsPage = new ReferralTypeComponentsPage();
        expect(await referralTypeComponentsPage.getTitle()).to.eq('cwcrm2App.referralType.home.title');
    });

    it('should load create ReferralType page', async () => {
        await referralTypeComponentsPage.clickOnCreateButton();
        referralTypeUpdatePage = new ReferralTypeUpdatePage();
        expect(await referralTypeUpdatePage.getPageTitle()).to.eq('cwcrm2App.referralType.home.createOrEditLabel');
        await referralTypeUpdatePage.cancel();
    });

    it('should create and save ReferralTypes', async () => {
        const nbButtonsBeforeCreate = await referralTypeComponentsPage.countDeleteButtons();

        await referralTypeComponentsPage.clickOnCreateButton();
        await promise.all([referralTypeUpdatePage.setNameInput('name')]);
        expect(await referralTypeUpdatePage.getNameInput()).to.eq('name');
        await referralTypeUpdatePage.save();
        expect(await referralTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await referralTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ReferralType', async () => {
        const nbButtonsBeforeDelete = await referralTypeComponentsPage.countDeleteButtons();
        await referralTypeComponentsPage.clickOnLastDeleteButton();

        referralTypeDeleteDialog = new ReferralTypeDeleteDialog();
        expect(await referralTypeDeleteDialog.getDialogTitle()).to.eq('cwcrm2App.referralType.delete.question');
        await referralTypeDeleteDialog.clickOnConfirmButton();

        expect(await referralTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
