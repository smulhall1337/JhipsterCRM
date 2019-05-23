/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactHistoryComponentsPage, ContactHistoryDeleteDialog, ContactHistoryUpdatePage } from './contact-history.page-object';

const expect = chai.expect;

describe('ContactHistory e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contactHistoryUpdatePage: ContactHistoryUpdatePage;
    let contactHistoryComponentsPage: ContactHistoryComponentsPage;
    /*let contactHistoryDeleteDialog: ContactHistoryDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ContactHistories', async () => {
        await navBarPage.goToEntity('contact-history');
        contactHistoryComponentsPage = new ContactHistoryComponentsPage();
        await browser.wait(ec.visibilityOf(contactHistoryComponentsPage.title), 5000);
        expect(await contactHistoryComponentsPage.getTitle()).to.eq('cwcrm2App.contactHistory.home.title');
    });

    it('should load create ContactHistory page', async () => {
        await contactHistoryComponentsPage.clickOnCreateButton();
        contactHistoryUpdatePage = new ContactHistoryUpdatePage();
        expect(await contactHistoryUpdatePage.getPageTitle()).to.eq('cwcrm2App.contactHistory.home.createOrEditLabel');
        await contactHistoryUpdatePage.cancel();
    });

    /* it('should create and save ContactHistories', async () => {
        const nbButtonsBeforeCreate = await contactHistoryComponentsPage.countDeleteButtons();

        await contactHistoryComponentsPage.clickOnCreateButton();
        await promise.all([
            contactHistoryUpdatePage.setDateInput('2000-12-31'),
            contactHistoryUpdatePage.setNotesInput('notes'),
            contactHistoryUpdatePage.setStatusInput('5'),
            contactHistoryUpdatePage.participantSelectLastOption(),
            contactHistoryUpdatePage.userSelectLastOption(),
            contactHistoryUpdatePage.contactTypeSelectLastOption(),
            contactHistoryUpdatePage.contactSubTypeSelectLastOption(),
        ]);
        expect(await contactHistoryUpdatePage.getDateInput()).to.eq('2000-12-31');
        expect(await contactHistoryUpdatePage.getNotesInput()).to.eq('notes');
        expect(await contactHistoryUpdatePage.getStatusInput()).to.eq('5');
        await contactHistoryUpdatePage.save();
        expect(await contactHistoryUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contactHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last ContactHistory', async () => {
        const nbButtonsBeforeDelete = await contactHistoryComponentsPage.countDeleteButtons();
        await contactHistoryComponentsPage.clickOnLastDeleteButton();

        contactHistoryDeleteDialog = new ContactHistoryDeleteDialog();
        expect(await contactHistoryDeleteDialog.getDialogTitle())
            .to.eq('cwcrm2App.contactHistory.delete.question');
        await contactHistoryDeleteDialog.clickOnConfirmButton();

        expect(await contactHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
