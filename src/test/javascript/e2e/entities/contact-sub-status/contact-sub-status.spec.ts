/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactSubStatusComponentsPage, ContactSubStatusDeleteDialog, ContactSubStatusUpdatePage } from './contact-sub-status.page-object';

const expect = chai.expect;

describe('ContactSubStatus e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contactSubStatusUpdatePage: ContactSubStatusUpdatePage;
    let contactSubStatusComponentsPage: ContactSubStatusComponentsPage;
    /*let contactSubStatusDeleteDialog: ContactSubStatusDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ContactSubStatuses', async () => {
        await navBarPage.goToEntity('contact-sub-status');
        contactSubStatusComponentsPage = new ContactSubStatusComponentsPage();
        expect(await contactSubStatusComponentsPage.getTitle()).to.eq('cwcrm2App.contactSubStatus.home.title');
    });

    it('should load create ContactSubStatus page', async () => {
        await contactSubStatusComponentsPage.clickOnCreateButton();
        contactSubStatusUpdatePage = new ContactSubStatusUpdatePage();
        expect(await contactSubStatusUpdatePage.getPageTitle()).to.eq('cwcrm2App.contactSubStatus.home.createOrEditLabel');
        await contactSubStatusUpdatePage.cancel();
    });

    /* it('should create and save ContactSubStatuses', async () => {
        const nbButtonsBeforeCreate = await contactSubStatusComponentsPage.countDeleteButtons();

        await contactSubStatusComponentsPage.clickOnCreateButton();
        await promise.all([
            contactSubStatusUpdatePage.setNameInput('name'),
            contactSubStatusUpdatePage.subTypeOfSelectLastOption(),
        ]);
        expect(await contactSubStatusUpdatePage.getNameInput()).to.eq('name');
        await contactSubStatusUpdatePage.save();
        expect(await contactSubStatusUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contactSubStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last ContactSubStatus', async () => {
        const nbButtonsBeforeDelete = await contactSubStatusComponentsPage.countDeleteButtons();
        await contactSubStatusComponentsPage.clickOnLastDeleteButton();

        contactSubStatusDeleteDialog = new ContactSubStatusDeleteDialog();
        expect(await contactSubStatusDeleteDialog.getDialogTitle())
            .to.eq('cwcrm2App.contactSubStatus.delete.question');
        await contactSubStatusDeleteDialog.clickOnConfirmButton();

        expect(await contactSubStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
