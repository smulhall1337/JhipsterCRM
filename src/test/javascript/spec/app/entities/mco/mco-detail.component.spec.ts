/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { MCODetailComponent } from 'app/entities/mco/mco-detail.component';
import { MCO } from 'app/shared/model/mco.model';

describe('Component Tests', () => {
    describe('MCO Management Detail Component', () => {
        let comp: MCODetailComponent;
        let fixture: ComponentFixture<MCODetailComponent>;
        const route = ({ data: of({ mCO: new MCO(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [MCODetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MCODetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MCODetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mCO).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
