/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ParticipantNotesComponentsPage, ParticipantNotesDeleteDialog, ParticipantNotesUpdatePage } from './participant-notes.page-object';

const expect = chai.expect;

describe('ParticipantNotes e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let participantNotesUpdatePage: ParticipantNotesUpdatePage;
    let participantNotesComponentsPage: ParticipantNotesComponentsPage;
    let participantNotesDeleteDialog: ParticipantNotesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ParticipantNotes', async () => {
        await navBarPage.goToEntity('participant-notes');
        participantNotesComponentsPage = new ParticipantNotesComponentsPage();
        expect(await participantNotesComponentsPage.getTitle()).to.eq('cwcrm2App.participantNotes.home.title');
    });

    it('should load create ParticipantNotes page', async () => {
        await participantNotesComponentsPage.clickOnCreateButton();
        participantNotesUpdatePage = new ParticipantNotesUpdatePage();
        expect(await participantNotesUpdatePage.getPageTitle()).to.eq('cwcrm2App.participantNotes.home.createOrEditLabel');
        await participantNotesUpdatePage.cancel();
    });

    it('should create and save ParticipantNotes', async () => {
        const nbButtonsBeforeCreate = await participantNotesComponentsPage.countDeleteButtons();

        await participantNotesComponentsPage.clickOnCreateButton();
        await promise.all([
            participantNotesUpdatePage.setNotesInput('notes'),
            participantNotesUpdatePage.userSelectLastOption(),
            participantNotesUpdatePage.participantSelectLastOption()
        ]);
        expect(await participantNotesUpdatePage.getNotesInput()).to.eq('notes');
        await participantNotesUpdatePage.save();
        expect(await participantNotesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await participantNotesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ParticipantNotes', async () => {
        const nbButtonsBeforeDelete = await participantNotesComponentsPage.countDeleteButtons();
        await participantNotesComponentsPage.clickOnLastDeleteButton();

        participantNotesDeleteDialog = new ParticipantNotesDeleteDialog();
        expect(await participantNotesDeleteDialog.getDialogTitle()).to.eq('cwcrm2App.participantNotes.delete.question');
        await participantNotesDeleteDialog.clickOnConfirmButton();

        expect(await participantNotesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
