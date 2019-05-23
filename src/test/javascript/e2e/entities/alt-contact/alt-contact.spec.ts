/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AltContactComponentsPage, AltContactDeleteDialog, AltContactUpdatePage } from './alt-contact.page-object';

const expect = chai.expect;

describe('AltContact e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let altContactUpdatePage: AltContactUpdatePage;
    let altContactComponentsPage: AltContactComponentsPage;
    let altContactDeleteDialog: AltContactDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AltContacts', async () => {
        await navBarPage.goToEntity('alt-contact');
        altContactComponentsPage = new AltContactComponentsPage();
        await browser.wait(ec.visibilityOf(altContactComponentsPage.title), 5000);
        expect(await altContactComponentsPage.getTitle()).to.eq('cwcrm2App.altContact.home.title');
    });

    it('should load create AltContact page', async () => {
        await altContactComponentsPage.clickOnCreateButton();
        altContactUpdatePage = new AltContactUpdatePage();
        expect(await altContactUpdatePage.getPageTitle()).to.eq('cwcrm2App.altContact.home.createOrEditLabel');
        await altContactUpdatePage.cancel();
    });

    it('should create and save AltContacts', async () => {
        const nbButtonsBeforeCreate = await altContactComponentsPage.countDeleteButtons();

        await altContactComponentsPage.clickOnCreateButton();
        await promise.all([
            altContactUpdatePage.setNameInput('name'),
            altContactUpdatePage.setRelationshipInput('relationship'),
            altContactUpdatePage.setPhoneInput('phone'),
            altContactUpdatePage.setNotesInput('notes'),
            altContactUpdatePage.participantSelectLastOption(),
            altContactUpdatePage.createdBySelectLastOption()
        ]);
        expect(await altContactUpdatePage.getNameInput()).to.eq('name');
        expect(await altContactUpdatePage.getRelationshipInput()).to.eq('relationship');
        expect(await altContactUpdatePage.getPhoneInput()).to.eq('phone');
        expect(await altContactUpdatePage.getNotesInput()).to.eq('notes');
        await altContactUpdatePage.save();
        expect(await altContactUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await altContactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AltContact', async () => {
        const nbButtonsBeforeDelete = await altContactComponentsPage.countDeleteButtons();
        await altContactComponentsPage.clickOnLastDeleteButton();

        altContactDeleteDialog = new AltContactDeleteDialog();
        expect(await altContactDeleteDialog.getDialogTitle()).to.eq('cwcrm2App.altContact.delete.question');
        await altContactDeleteDialog.clickOnConfirmButton();

        expect(await altContactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
