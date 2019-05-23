/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ParticipantNotesUpdateComponent } from 'app/entities/participant-notes/participant-notes-update.component';
import { ParticipantNotesService } from 'app/entities/participant-notes/participant-notes.service';
import { ParticipantNotes } from 'app/shared/model/participant-notes.model';

describe('Component Tests', () => {
    describe('ParticipantNotes Management Update Component', () => {
        let comp: ParticipantNotesUpdateComponent;
        let fixture: ComponentFixture<ParticipantNotesUpdateComponent>;
        let service: ParticipantNotesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ParticipantNotesUpdateComponent]
            })
                .overrideTemplate(ParticipantNotesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParticipantNotesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParticipantNotesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ParticipantNotes(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.participantNotes = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ParticipantNotes();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.participantNotes = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
