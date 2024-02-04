import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKariaUser } from '../karia-user.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../karia-user.test-samples';

import { KariaUserService } from './karia-user.service';

const requireRestSample: IKariaUser = {
  ...sampleWithRequiredData,
};

describe('KariaUser Service', () => {
  let service: KariaUserService;
  let httpMock: HttpTestingController;
  let expectedResult: IKariaUser | IKariaUser[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KariaUserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a KariaUser', () => {
      const kariaUser = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(kariaUser).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KariaUser', () => {
      const kariaUser = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(kariaUser).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KariaUser', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KariaUser', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KariaUser', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKariaUserToCollectionIfMissing', () => {
      it('should add a KariaUser to an empty array', () => {
        const kariaUser: IKariaUser = sampleWithRequiredData;
        expectedResult = service.addKariaUserToCollectionIfMissing([], kariaUser);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kariaUser);
      });

      it('should not add a KariaUser to an array that contains it', () => {
        const kariaUser: IKariaUser = sampleWithRequiredData;
        const kariaUserCollection: IKariaUser[] = [
          {
            ...kariaUser,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKariaUserToCollectionIfMissing(kariaUserCollection, kariaUser);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KariaUser to an array that doesn't contain it", () => {
        const kariaUser: IKariaUser = sampleWithRequiredData;
        const kariaUserCollection: IKariaUser[] = [sampleWithPartialData];
        expectedResult = service.addKariaUserToCollectionIfMissing(kariaUserCollection, kariaUser);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kariaUser);
      });

      it('should add only unique KariaUser to an array', () => {
        const kariaUserArray: IKariaUser[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const kariaUserCollection: IKariaUser[] = [sampleWithRequiredData];
        expectedResult = service.addKariaUserToCollectionIfMissing(kariaUserCollection, ...kariaUserArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const kariaUser: IKariaUser = sampleWithRequiredData;
        const kariaUser2: IKariaUser = sampleWithPartialData;
        expectedResult = service.addKariaUserToCollectionIfMissing([], kariaUser, kariaUser2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(kariaUser);
        expect(expectedResult).toContain(kariaUser2);
      });

      it('should accept null and undefined values', () => {
        const kariaUser: IKariaUser = sampleWithRequiredData;
        expectedResult = service.addKariaUserToCollectionIfMissing([], null, kariaUser, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(kariaUser);
      });

      it('should return initial array if no KariaUser is added', () => {
        const kariaUserCollection: IKariaUser[] = [sampleWithRequiredData];
        expectedResult = service.addKariaUserToCollectionIfMissing(kariaUserCollection, undefined, null);
        expect(expectedResult).toEqual(kariaUserCollection);
      });
    });

    describe('compareKariaUser', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKariaUser(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKariaUser(entity1, entity2);
        const compareResult2 = service.compareKariaUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKariaUser(entity1, entity2);
        const compareResult2 = service.compareKariaUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKariaUser(entity1, entity2);
        const compareResult2 = service.compareKariaUser(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
