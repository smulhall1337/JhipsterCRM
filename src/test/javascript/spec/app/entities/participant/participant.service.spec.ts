/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ParticipantService } from 'app/entities/participant/participant.service';
import { IParticipant, Participant } from 'app/shared/model/participant.model';

describe('Service Tests', () => {
    describe('Participant Service', () => {
        let injector: TestBed;
        let service: ParticipantService;
        let httpMock: HttpTestingController;
        let elemDefault: IParticipant;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ParticipantService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Participant(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                currentDate,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        registrationDate: currentDate.format(DATE_FORMAT),
                        dob: currentDate.format(DATE_FORMAT),
                        dateAuthorized: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Participant', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        registrationDate: currentDate.format(DATE_FORMAT),
                        dob: currentDate.format(DATE_FORMAT),
                        dateAuthorized: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        registrationDate: currentDate,
                        dob: currentDate,
                        dateAuthorized: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Participant(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Participant', async () => {
                const returnedFromService = Object.assign(
                    {
                        firstName: 'BBBBBB',
                        middleInitial: 'BBBBBB',
                        lastName: 'BBBBBB',
                        title: 'BBBBBB',
                        registrationDate: currentDate.format(DATE_FORMAT),
                        address: 'BBBBBB',
                        city: 'BBBBBB',
                        state: 'BBBBBB',
                        dob: currentDate.format(DATE_FORMAT),
                        primaryPhone: 'BBBBBB',
                        primaryPhoneType: 'BBBBBB',
                        secondaryPhone: 'BBBBBB',
                        secondaryPhoneType: 'BBBBBB',
                        email: 'BBBBBB',
                        zip: 'BBBBBB',
                        medicareIdNumber: 'BBBBBB',
                        medicaidIdNumber: 'BBBBBB',
                        gender: 'BBBBBB',
                        participantStatus: 1,
                        county: 'BBBBBB',
                        dateAuthorized: currentDate.format(DATE_FORMAT),
                        authorizationNumber: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        registrationDate: currentDate,
                        dob: currentDate,
                        dateAuthorized: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Participant', async () => {
                const returnedFromService = Object.assign(
                    {
                        firstName: 'BBBBBB',
                        middleInitial: 'BBBBBB',
                        lastName: 'BBBBBB',
                        title: 'BBBBBB',
                        registrationDate: currentDate.format(DATE_FORMAT),
                        address: 'BBBBBB',
                        city: 'BBBBBB',
                        state: 'BBBBBB',
                        dob: currentDate.format(DATE_FORMAT),
                        primaryPhone: 'BBBBBB',
                        primaryPhoneType: 'BBBBBB',
                        secondaryPhone: 'BBBBBB',
                        secondaryPhoneType: 'BBBBBB',
                        email: 'BBBBBB',
                        zip: 'BBBBBB',
                        medicareIdNumber: 'BBBBBB',
                        medicaidIdNumber: 'BBBBBB',
                        gender: 'BBBBBB',
                        participantStatus: 1,
                        county: 'BBBBBB',
                        dateAuthorized: currentDate.format(DATE_FORMAT),
                        authorizationNumber: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        registrationDate: currentDate,
                        dob: currentDate,
                        dateAuthorized: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Participant', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
