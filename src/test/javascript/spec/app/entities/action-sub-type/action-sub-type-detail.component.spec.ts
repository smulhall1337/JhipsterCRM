/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { ActionSubTypeDetailComponent } from 'app/entities/action-sub-type/action-sub-type-detail.component';
import { ActionSubType } from 'app/shared/model/action-sub-type.model';

describe('Component Tests', () => {
    describe('ActionSubType Management Detail Component', () => {
        let comp: ActionSubTypeDetailComponent;
        let fixture: ComponentFixture<ActionSubTypeDetailComponent>;
        const route = ({ data: of({ actionSubType: new ActionSubType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ActionSubTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ActionSubTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActionSubTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.actionSubType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
