/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Cwcrm2TestModule } from '../../../test.module';
import { ParticipantNotesDeleteDialogComponent } from 'app/entities/participant-notes/participant-notes-delete-dialog.component';
import { ParticipantNotesService } from 'app/entities/participant-notes/participant-notes.service';

describe('Component Tests', () => {
    describe('ParticipantNotes Management Delete Component', () => {
        let comp: ParticipantNotesDeleteDialogComponent;
        let fixture: ComponentFixture<ParticipantNotesDeleteDialogComponent>;
        let service: ParticipantNotesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ParticipantNotesDeleteDialogComponent]
            })
                .overrideTemplate(ParticipantNotesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParticipantNotesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParticipantNotesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
