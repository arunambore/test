/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InvitationLivexService } from 'app/entities/usermanagement/invitation-livex/invitation-livex.service';
import { IInvitationLivex, InvitationLivex } from 'app/shared/model/usermanagement/invitation-livex.model';

describe('Service Tests', () => {
  describe('InvitationLivex Service', () => {
    let injector: TestBed;
    let service: InvitationLivexService;
    let httpMock: HttpTestingController;
    let elemDefault: IInvitationLivex;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      injector = getTestBed();
      service = injector.get(InvitationLivexService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InvitationLivex(0, 'AAAAAAA', 0, currentDate, currentDate, false);
    });

    describe('Service methods', async () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            invitationDate: currentDate.format(DATE_FORMAT),
            validTill: currentDate.format(DATE_FORMAT)
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

      it('should create a InvitationLivex', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            invitationDate: currentDate.format(DATE_FORMAT),
            validTill: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            invitationDate: currentDate,
            validTill: currentDate
          },
          returnedFromService
        );
        service
          .create(new InvitationLivex(null))
          .pipe(take(1))
          .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(JSON.stringify(returnedFromService));
      });

      it('should update a InvitationLivex', async () => {
        const returnedFromService = Object.assign(
          {
            key: 'BBBBBB',
            pin: 1,
            invitationDate: currentDate.format(DATE_FORMAT),
            validTill: currentDate.format(DATE_FORMAT),
            isInvalid: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invitationDate: currentDate,
            validTill: currentDate
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

      it('should return a list of InvitationLivex', async () => {
        const returnedFromService = Object.assign(
          {
            key: 'BBBBBB',
            pin: 1,
            invitationDate: currentDate.format(DATE_FORMAT),
            validTill: currentDate.format(DATE_FORMAT),
            isInvalid: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            invitationDate: currentDate,
            validTill: currentDate
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

      it('should delete a InvitationLivex', async () => {
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
