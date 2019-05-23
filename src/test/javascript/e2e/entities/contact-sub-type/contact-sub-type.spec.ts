/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactSubTypeComponentsPage, ContactSubTypeDeleteDialog, ContactSubTypeUpdatePage } from './contact-sub-type.page-object';

const expect = chai.expect;

describe('ContactSubType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contactSubTypeUpdatePage: ContactSubTypeUpdatePage;
    let contactSubTypeComponentsPage: ContactSubTypeComponentsPage;
    let contactSubTypeDeleteDialog: ContactSubTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ContactSubTypes', async () => {
        await navBarPage.goToEntity('contact-sub-type');
        contactSubTypeComponentsPage = new ContactSubTypeComponentsPage();
        await browser.wait(ec.visibilityOf(contactSubTypeComponentsPage.title), 5000);
        expect(await contactSubTypeComponentsPage.getTitle()).to.eq('cwcrm2App.contactSubType.home.title');
    });

    it('should load create ContactSubType page', async () => {
        await contactSubTypeComponentsPage.clickOnCreateButton();
        contactSubTypeUpdatePage = new ContactSubTypeUpdatePage();
        expect(await contactSubTypeUpdatePage.getPageTitle()).to.eq('cwcrm2App.contactSubType.home.createOrEditLabel');
        await contactSubTypeUpdatePage.cancel();
    });

    it('should create and save ContactSubTypes', async () => {
        const nbButtonsBeforeCreate = await contactSubTypeComponentsPage.countDeleteButtons();

        await contactSubTypeComponentsPage.clickOnCreateButton();
        await promise.all([contactSubTypeUpdatePage.setNameInput('name')]);
        expect(await contactSubTypeUpdatePage.getNameInput()).to.eq('name');
        await contactSubTypeUpdatePage.save();
        expect(await contactSubTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contactSubTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ContactSubType', async () => {
        const nbButtonsBeforeDelete = await contactSubTypeComponentsPage.countDeleteButtons();
        await contactSubTypeComponentsPage.clickOnLastDeleteButton();

        contactSubTypeDeleteDialog = new ContactSubTypeDeleteDialog();
        expect(await contactSubTypeDeleteDialog.getDialogTitle()).to.eq('cwcrm2App.contactSubType.delete.question');
        await contactSubTypeDeleteDialog.clickOnConfirmButton();

        expect(await contactSubTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
