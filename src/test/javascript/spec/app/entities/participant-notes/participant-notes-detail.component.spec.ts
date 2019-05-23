/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ParticipantNotesDetailComponent } from 'app/entities/participant-notes/participant-notes-detail.component';
import { ParticipantNotes } from 'app/shared/model/participant-notes.model';

describe('Component Tests', () => {
    describe('ParticipantNotes Management Detail Component', () => {
        let comp: ParticipantNotesDetailComponent;
        let fixture: ComponentFixture<ParticipantNotesDetailComponent>;
        const route = ({ data: of({ participantNotes: new ParticipantNotes(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ParticipantNotesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ParticipantNotesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParticipantNotesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.participantNotes).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
