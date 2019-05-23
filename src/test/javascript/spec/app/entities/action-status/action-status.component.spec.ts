/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Cwcrm2TestModule } from '../../../test.module';
import { ActionStatusComponent } from 'app/entities/action-status/action-status.component';
import { ActionStatusService } from 'app/entities/action-status/action-status.service';
import { ActionStatus } from 'app/shared/model/action-status.model';

describe('Component Tests', () => {
    describe('ActionStatus Management Component', () => {
        let comp: ActionStatusComponent;
        let fixture: ComponentFixture<ActionStatusComponent>;
        let service: ActionStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [ActionStatusComponent],
                providers: []
            })
                .overrideTemplate(ActionStatusComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActionStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionStatusService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ActionStatus(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.actionStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
