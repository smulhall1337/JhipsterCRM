/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactStatusComponentsPage, ContactStatusDeleteDialog, ContactStatusUpdatePage } from './contact-status.page-object';

const expect = chai.expect;

describe('ContactStatus e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contactStatusUpdatePage: ContactStatusUpdatePage;
    let contactStatusComponentsPage: ContactStatusComponentsPage;
    let contactStatusDeleteDialog: ContactStatusDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ContactStatuses', async () => {
        await navBarPage.goToEntity('contact-status');
        contactStatusComponentsPage = new ContactStatusComponentsPage();
        expect(await contactStatusComponentsPage.getTitle()).to.eq('cwcrm2App.contactStatus.home.title');
    });

    it('should load create ContactStatus page', async () => {
        await contactStatusComponentsPage.clickOnCreateButton();
        contactStatusUpdatePage = new ContactStatusUpdatePage();
        expect(await contactStatusUpdatePage.getPageTitle()).to.eq('cwcrm2App.contactStatus.home.createOrEditLabel');
        await contactStatusUpdatePage.cancel();
    });

    it('should create and save ContactStatuses', async () => {
        const nbButtonsBeforeCreate = await contactStatusComponentsPage.countDeleteButtons();

        await contactStatusComponentsPage.clickOnCreateButton();
        await promise.all([contactStatusUpdatePage.setNameInput('name')]);
        expect(await contactStatusUpdatePage.getNameInput()).to.eq('name');
        await contactStatusUpdatePage.save();
        expect(await contactStatusUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contactStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ContactStatus', async () => {
        const nbButtonsBeforeDelete = await contactStatusComponentsPage.countDeleteButtons();
        await contactStatusComponentsPage.clickOnLastDeleteButton();

        contactStatusDeleteDialog = new ContactStatusDeleteDialog();
        expect(await contactStatusDeleteDialog.getDialogTitle()).to.eq('cwcrm2App.contactStatus.delete.question');
        await contactStatusDeleteDialog.clickOnConfirmButton();

        expect(await contactStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
