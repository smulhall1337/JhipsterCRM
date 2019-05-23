/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { MCOComponent } from 'app/entities/mco/mco.component';
import { MCOService } from 'app/entities/mco/mco.service';
import { MCO } from 'app/shared/model/mco.model';

describe('Component Tests', () => {
    describe('MCO Management Component', () => {
        let comp: MCOComponent;
        let fixture: ComponentFixture<MCOComponent>;
        let service: MCOService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [MCOComponent],
                providers: []
            })
                .overrideTemplate(MCOComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MCOComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MCOService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MCO(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.mCOS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
