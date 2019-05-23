/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ActionStatusDetailComponent } from 'app/entities/action-status/action-status-detail.component';
import { ActionStatus } from 'app/shared/model/action-status.model';

describe('Component Tests', () => {
    describe('ActionStatus Management Detail Component', () => {
        let comp: ActionStatusDetailComponent;
        let fixture: ComponentFixture<ActionStatusDetailComponent>;
        const route = ({ data: of({ actionStatus: new ActionStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ActionStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ActionStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActionStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.actionStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
