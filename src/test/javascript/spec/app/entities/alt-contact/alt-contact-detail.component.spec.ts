/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Cwcrm2TestModule } from '../../../test.module';
import { AltContactDetailComponent } from 'app/entities/alt-contact/alt-contact-detail.component';
import { AltContact } from 'app/shared/model/alt-contact.model';

describe('Component Tests', () => {
    describe('AltContact Management Detail Component', () => {
        let comp: AltContactDetailComponent;
        let fixture: ComponentFixture<AltContactDetailComponent>;
        const route = ({ data: of({ altContact: new AltContact(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Cwcrm2TestModule],
                declarations: [AltContactDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AltContactDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AltContactDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.altContact).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
