/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { ParticipantNotesComponent } from 'app/entities/participant-notes/participant-notes.component';
import { ParticipantNotesService } from 'app/entities/participant-notes/participant-notes.service';
import { ParticipantNotes } from 'app/shared/model/participant-notes.model';

describe('Component Tests', () => {
    describe('ParticipantNotes Management Component', () => {
        let comp: ParticipantNotesComponent;
        let fixture: ComponentFixture<ParticipantNotesComponent>;
        let service: ParticipantNotesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ParticipantNotesComponent],
                providers: []
            })
                .overrideTemplate(ParticipantNotesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParticipantNotesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParticipantNotesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ParticipantNotes(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.participantNotes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
